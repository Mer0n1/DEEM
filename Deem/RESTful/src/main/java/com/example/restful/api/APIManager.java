package com.example.restful.api;


import com.example.restful.api.websocket.PushClient;
import com.example.restful.models.Account;
import com.example.restful.models.AuthRequest;
import com.example.restful.models.Chat;
import com.example.restful.models.Club;
import com.example.restful.models.Event;
import com.example.restful.models.Group;
import com.example.restful.models.IconImage;
import com.example.restful.models.Image;
import com.example.restful.models.ImageLoadCallback;
import com.example.restful.models.Message;
import com.example.restful.models.MessageImage;
import com.example.restful.models.News;
import com.example.restful.models.NewsImage;
import com.example.restful.models.PrivateAccountDTO;
import com.example.restful.models.PublicAccountDTO;
import com.example.restful.models.StandardCallback;
import com.example.restful.models.TopLoadCallback;
import com.example.restful.models.TopsUsers;
import com.example.restful.models.curriculum.Class;
import com.example.restful.models.curriculum.DayliSchedule;
import com.example.restful.utils.DateTranslator;
import com.example.restful.utils.DateUtil;
import com.example.restful.utils.GeneratorUUID;

import org.graalvm.compiler.api.replacements.Snippet;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class APIManager {

    private static APIManager manager;
    private static PushClient pushClient;
    public  static ServerStatusInfo statusInfo;

    public volatile List<Account> listAccounts;
    public volatile List<Group> listGroups;
    public volatile List<Group> listGroupsOfFaculty;
    public volatile List<Group> adminGroups;
    public volatile List<Chat> listChats;
    public volatile List<News> listNews;
    public volatile List<Event> listEvents;
    public volatile List<Club> listClubs;
    public volatile List<DayliSchedule> dayliSchedules;
    private volatile TopsUsers topsUsers;
    private volatile List<Group> groupClubs;

    public Account myAccount;

    private String jwtKey;

    private static ModelMapper modelMapper;

    private APIManager() {
        topsUsers = new TopsUsers();
        pushClient = new PushClient();
        statusInfo = new ServerStatusInfo();
        modelMapper = new ModelMapper();

        myAccount = new Account();
        listAccounts = new ArrayList<>();
        listGroups = new ArrayList<>();
        listGroupsOfFaculty = new ArrayList<>();
        listChats = new ArrayList<>();
        listNews = new ArrayList<>();
        listEvents = new ArrayList<>();
        adminGroups = new ArrayList<>();
        dayliSchedules = new ArrayList<>();
        groupClubs = new ArrayList<>();
        listClubs = new ArrayList<>();
    }

    public static APIManager getManager() {
        if (manager == null)
            manager = new APIManager();

        return manager;
    }

    public boolean isAuth() {
        return Handler.getInstance().isAuth();
    }

    public void auth(AuthRequest authRequest) {

        final Call<String> str = Repository.getInstance().login(authRequest);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Response<String> stri = null;
                try {
                    stri = str.execute();
                    Handler.setToken(stri.body());
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                jwtKey = stri.body();
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pushClient.auth(jwtKey);
    }

    public void getMyAccount() {
        Call<PublicAccountDTO> str = Repository.getInstance().getMyAccount(Handler.getToken());
        Callback<PublicAccountDTO> cl = new Callback<PublicAccountDTO>() {
            @Override
            public void onResponse(Call<PublicAccountDTO> call, Response<PublicAccountDTO> response) {
                if (response.body() != null) {
                    PublicAccountDTO dto = response.body();
                    myAccount = convertToAccount(dto);
                }
            }

            @Override
            public void onFailure(Call<PublicAccountDTO> call, Throwable t) {

            }
        };

        str.enqueue(cl);
    }


    public void UpdateData() {

        final Call<PublicAccountDTO> str = Repository.getInstance().getMyAccount();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Response<PublicAccountDTO> stri = null;
                try {
                    stri = str.execute();
                    PublicAccountDTO dto = stri.body();
                    myAccount = convertToAccount(dto);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Update accounts
        Repository.getInstance().getAccounts()
                .enqueue(new Callback<List<PrivateAccountDTO>>() {
                    @Override
                    public void onResponse(Call<List<PrivateAccountDTO>> call, Response<List<PrivateAccountDTO>> response) {
                        List<PrivateAccountDTO> listDto = response.body();

                        listAccounts.clear();

                        for (PrivateAccountDTO dto : listDto)
                            listAccounts.add(convertToAccount(dto));

                        if (listAccounts.size() != 0)
                            statusInfo.AccountListGot = true;


                        //загрузим иконки аккаунтов standard
                        /*for (Account account : listAccounts)
                            Repository.getInstance().getImage(
                                    GeneratorUUID.getInstance().generateUUIDForIcon(account.getUsername()), "profile_icon")
                                    .enqueue(new Callback<Image>() {
                                        @Override
                                        public void onResponse(Call<Image> call, Response<Image> response) {
                                            account.setImageIcon(response.body());
                                        }

                                        @Override
                                        public void onFailure(Call<Image> call, Throwable t) {

                                        }
                                    });*/
                    }

                    @Override
                    public void onFailure(Call<List<PrivateAccountDTO>> call, Throwable t) {

                    }
                }
        );


        //
        Repository.getInstance().getGroups().enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                listGroups = response.body();
                buildGroups();

                if (listGroups != null) {
                    statusInfo.GroupsListGot = true;

                    //clubs
                    Repository.getInstance().getClubs().enqueue(new Callback<List<Club>>() {
                        @Override
                        public void onResponse(Call<List<Club>> call, Response<List<Club>> response) {
                            if (response.body() != null) {
                                statusInfo.ClubListGot = true;
                                listClubs = response.body();
                                buildClubs();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Club>> call, Throwable t) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                System.err.println("Failure ошибка групп " + t.getMessage());
            }
        });

        //
        Repository.getInstance().getChats().enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                listChats = response.body();
                if (listChats != null)
                    statusInfo.ChatsListGot = true;
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                System.err.println("Failure ошибка чатов " + t.getMessage());
            }
        });


        /*Repository.getInstance().getNews().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                listNews = response.body();
                if (listNews != null)
                    statusInfo.NewsListGot = true;
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {

            }
        });*/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
        String dateStr = sdf.format(new Date(System.currentTimeMillis()));
        updateNewsFeed(dateStr, new StandardCallback() {
            @Override
            public void call() {
                statusInfo.NewsListGot = true;
            }
        });


        Repository.getInstance().getEvents().enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                listEvents = response.body();

                if (listEvents != null)
                    statusInfo.EventsListGot = true;
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {

            }
        });

        Repository.getInstance().getClasses().enqueue(new Callback<List<Class>>() {
            @Override
            public void onResponse(Call<List<Class>> call, Response<List<Class>> response) {

                if (response.body() == null)
                    return;
                else
                    statusInfo.TeacherListClassesGot = true;

                buildClasses(response.body());
            }

            @Override
            public void onFailure(Call<List<Class>> call, Throwable t) {

            }
        });


    }


    //---
    public void updateNewsFeed(String date, StandardCallback callback) {
        Repository.getInstance().getNewsFeed(date).enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if (response.body() != null) {
                    listNews.addAll(response.body());
                    callback.call();
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {

            }
        });
    }

    //--------send

    public void sendMessage(Message message) {
        Repository.getInstance().sendMessage(message).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void sendNewChat(Chat chat) {
        Repository.getInstance().sendNewChat(chat).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

    public void addNews(News news) {
        Repository.getInstance().createNews(news).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }


    /***** ImageService ******/

    public void GetImage(String UUID, String type, ImageLoadCallback imageLoadCallback) {
        Repository.getInstance().getImage(UUID, type).enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {
                if (response.body() != null)
                    imageLoadCallback.onImageLoaded(response.body().getImgEncode());
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {
                System.err.println("RESPONSE FAILURE " + t.getMessage().toString());

            }
        });
    }

    public void addIcon(IconImage img) {
        Repository.getInstance().addIcon(img).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    //
    public void getTopStudentsFaculty(TopLoadCallback callback) {
        Repository.getInstance().getTopStudentsFaculty().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                topsUsers.topsUsersFaculty = response.body();
                callback.LoadTop(topsUsers);
                statusInfo.TopsListUsersUniversityGot = true;
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
    }

    public void getTopStudentsUniversity(TopLoadCallback callback) {
        Repository.getInstance().getTopStudentsUniversity().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                topsUsers.topsUsersUniversity = response.body();
                callback.LoadTop(topsUsers);
                statusInfo.TopsListUsersFacultyGot = true;
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
    }

    /** Lazy метод. Подгружаем картинки для новости и храним их в кэше*/
    public void getNewsImagesLazy(News news, ImageLoadCallback imageLoadCallback) {

        if (news == null) return;
        if (news.getImages() == null)
            news.setImages(new ArrayList<>());
        news.getImages().clear();

        Repository.getInstance().getCountImages(news.getId(), "news_image").enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Integer count = response.body();

                if (count == null) return;

                for (int j = 0; j < count; j++) {
                    Group group = listGroups.stream().filter(x->x.getId()==news.getIdGroup()).findAny().orElse(null);
                    if (group == null) return;
                    String author = group.getName();

                    String UUID = GeneratorUUID.getInstance().generateUUIDForNews(
                            DateUtil.getInstance().getDateToForm(news.getDate()), author);

                    Repository.getInstance().getImage(UUID, "news_image").enqueue(new Callback<Image>() {
                        @Override
                        public void onResponse(Call<Image> call, Response<Image> response) {
                            if (response.body() != null) {
                                NewsImage newsImage = new NewsImage();
                                newsImage.setImage(response.body());

                                news.getImages().add(newsImage);
                                imageLoadCallback.onImageLoaded(response.body().getImgEncode());
                            }
                        }

                        @Override
                        public void onFailure(Call<Image> call, Throwable t) {}
                    });
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {}
        });
    }

    public void getIconImageLazy(Account account, ImageLoadCallback imageLoadCallback) {
        if (account == null) return;

        String username = account.getUsername();
        if (username == null || username.isEmpty())
            return;

        String UUID = GeneratorUUID.getInstance().generateUUIDForIcon(username);

        Repository.getInstance().getImage(UUID, "profile_icon").enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {
                if (response.body() != null) {
                    account.setImageIcon(response.body());
                    imageLoadCallback.onImageLoaded(response.body().getImgEncode());
                }
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {

            }
        });
    }

    public void getMessageImagesLazy(Message message, ImageLoadCallback imageLoadCallback) {
        if (message == null) return;
        if (message.getImages() == null)
            message.setImages(new ArrayList<>());

        Repository.getInstance().getCountImages(message.getId(), "message_image").enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Integer count = response.body();
                if (count == null) return;
                if (count == 0)
                    message.setNoMessages(true);

                for (int j = 0; j < count; j++) {
                    Account account = listAccounts.stream().filter(x->x.getId()
                            ==message.getAuthor()).findAny().orElse(null);
                    if (account == null) return;
                    String author = account.getUsername();

                    String UUID = GeneratorUUID.getInstance().generateUUIDForMessage(
                            DateUtil.getInstance().getDateToForm(message.getDate()), author);

                    Repository.getInstance().getImage(UUID, "message_image").enqueue(new Callback<Image>() {
                        @Override
                        public void onResponse(Call<Image> call, Response<Image> response) {
                            if (response.body() != null) {
                                MessageImage messageImage = new MessageImage();
                                messageImage.setImage(response.body());

                                message.getImages().add(messageImage);
                                imageLoadCallback.onImageLoaded(response.body().getImgEncode());
                            }
                        }

                        @Override
                        public void onFailure(Call<Image> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }

    ///////////////////////////////////////////
    private Account convertToAccount(PrivateAccountDTO dto) {
        return modelMapper.map(dto, Account.class);
    }

    private Account convertToAccount(PublicAccountDTO account) {
        return modelMapper.map(account, Account.class);
    }

    private void buildGroups() {

        if (listGroups != null) {

            //Разделим на клубы и стандартные группы
            /*for (Group group : listGroups)
                if (group.getType().equals("club")) {
                    groupClubs.add(group);
                    listGroups.remove(group);
                }*/
            for (int j = 0; j < listGroups.size(); j++)
                if (listGroups.get(j).getType().equals("club")) {
                    groupClubs.add(listGroups.get(j));
                    listGroups.remove(listGroups.get(j));
                }

            //распределим аккаунты на каждую группу и группу на каждый аккаунт
            if (listAccounts != null)
                for (Group group : listGroups) {
                    if (group.getAccounts() != null)
                        continue;

                    List<Account> accounts = new ArrayList<>();
                    List<Long> users = group.getUsers();

                    for (Long i : users) {
                        Account account = listAccounts.stream().filter(x -> x.getId() == i).findAny().orElse(null);
                        if (account != null) {
                            accounts.add(account);
                            account.setGroup(group);
                        }
                    }

                    group.setAccounts(accounts);
                }


            //Сортировка по баллам сразу
            Collections.sort(listGroups, (s1, s2) -> {
                if (s1.getScore() > s2.getScore())
                    return -1;
                else return 0;
            });

            listGroupsOfFaculty.clear();
            adminGroups.clear();

            for (Group group : listGroups) {

                //сортируем группы для нашего факультета отдельно
                if (myAccount.getGroup().getFaculty().equals(group.getFaculty())
                        && group.getType().equals("standard"))
                    listGroupsOfFaculty.add(group);

                //соберем админ-группы
                if (group.getType().equals("admin"))
                    adminGroups.add(group);
            }

        }

    }

    public void buildClasses(List<Class> classes) {
        Collections.sort(classes, (s1, s2) -> {
            if (s1.getDate().before(s2.getDate()))
                return -1;
            else return 0;
        });

        Date currentDate = classes.get(0).getDate();
        List<Class> group = new ArrayList<>();

        for (Class cl : classes) {
            if (cl.getDate().getDate() == currentDate.getDate() &&
                    cl.getDate().getMonth() == currentDate.getMonth()) {
                group.add(cl);
                continue;
            }

            DayliSchedule dl = new DayliSchedule();
            dl.classes = new ArrayList<>(group);
            dl.date = dl.classes.get(0).getDate();
            dayliSchedules.add(dl);

            group.clear();
            group.add(cl);
            currentDate = cl.getDate();
        }

        if (group.size() != 0) {
            DayliSchedule dls = new DayliSchedule();
            dls.classes = new ArrayList<>(group);
            dls.date = dls.classes.get(0).getDate();
            dayliSchedules.add(dls);
        }
    }

    public void buildClubs() {

        if (!groupClubs.isEmpty()) {

            for (Club club : listClubs) {
                for (Group group : groupClubs)
                    if (club.getId_group() == group.getId()) {
                        club.setGroup(group);
                        break;
                    }

                for (Account account : listAccounts)
                    if (club.getId_leader() == account.getId()) {
                        club.setAccount(account);
                        break;
                    }
            }

        }
    }
}


