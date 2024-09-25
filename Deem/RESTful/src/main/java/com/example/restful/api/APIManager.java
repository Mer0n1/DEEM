package com.example.restful.api;


import androidx.lifecycle.MutableLiveData;

import com.example.restful.api.websocket.PushClient;
import com.example.restful.datebase.CacheStatusInfo;
import com.example.restful.datebase.CacheSystem;
import com.example.restful.models.Account;
import com.example.restful.models.AuthRequest;
import com.example.restful.models.Chat;
import com.example.restful.models.Club;
import com.example.restful.models.CreateMessageDTO;
import com.example.restful.models.CreateNewsDTO;
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
import com.example.restful.utils.DateUtil;
import com.example.restful.utils.GeneratorUUID;

//import org.graalvm.compiler.api.replacements.Snippet;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class APIManager {

    private static APIManager manager;
    private static PushClient pushClient;
    public  static ServerStatusInfo statusInfo;
    public  static CacheStatusInfo statusCacheInfo;

    public volatile List<Account> listAccounts;
    public volatile List<Group> listGroups;
    public volatile List<Group> listGroupsOfFaculty;
    public volatile List<Group> adminGroups;
    public volatile MutableLiveData<List<Chat>> listChats;
    public volatile MutableLiveData<List<News>> listNews;
    public volatile MutableLiveData<List<Event>> listEvents;
    public volatile MutableLiveData<List<Club>> listClubs;
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

        myAccount    = new Account();
        listAccounts = new ArrayList<>();
        listGroups   = new ArrayList<>();
        listGroupsOfFaculty = new ArrayList<>();
        listChats = new MutableLiveData<>();
        listNews  = new MutableLiveData<>();
        listEvents  = new MutableLiveData<>();
        adminGroups = new ArrayList<>();
        dayliSchedules = new ArrayList<>();
        groupClubs = new ArrayList<>();
        listClubs  = new MutableLiveData<>();


        /*
        Кэш система нужна для уменьшения нагрузки на сервер.
        Загрузка новостей: загружаем лишь актуальные новости. Актуальные это те дата которых позднее последней нашей новости, а также те которые были редактированы/удалены  ОК
        Загрузка сообщений: также загружаем только на момент входа в чат с последнего сообщения  ОК
        Загрузка ивентов: старые ивенты храним в кэше или где то еще а новые без исключения загружаем из сервера
        Загрузка групп и аккаунтов:
        Загрузка изображений: callback можно не заменять, однако думаю можно добавить LiveData на отслеживание списка изображений и обновлять кэш  ОК
        * */
        //сам подход buildData поменялся. Теперь мы билдим не единоразовую информацию, а только полученную. Иными словами
        //если раньше мы загружали весь список чатов для аккаунта который есть у нас, то теперь мы можем загружать его периодически
        //что означает что обновлять и билдить нужно только response.body

        //могут возникать ошибки в LiveData связанные с null и решение - делать setValue в конструкторе, тоесть здесь
        //мне кажется неверным подход с создание new MutableData и по идее это должно создаваться в констуркторе

        //нужно подумать над загрузкой переписки таким же методом как загузка новостной ленты
        //HalpPrivateAccount - аккаунт одногруппника где есть видимость баллов (либо сделать в PrivateAccountDTO)
        /*

        баги:
        баг с переписками. Групповая переписка
        баг. Иногда push не отправляется //как я понял основа этого не принятие запроса в сам контроллер но при этом сохранение сообщений
        исключения e.printStackTrace();  крашат приложения хотя можно сделать так чтобы мы обрабатывали ситуацию а не крашили, сейчас это тестирование
        баг который я так и не смог найти - изменение размера собственных item сообщений при пролистывании - вроде как какой то view перекрывает это

        тестирование

        Изменение дизайна
        сделать рейтинг в профиле
         */
    }

    public static APIManager getManager() {
        if (manager == null)
            manager = new APIManager();

        return manager;
    }

    public static void initialize() {
        //инициализируем в основном автоматическую систему кэша
        statusCacheInfo = CacheSystem.getCacheSystem().getCacheStatusInfo(); //строчку не трогать :)
        //CacheSystem.getCacheSystem().loadAll();
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

    public void UpdateData() {

        getMyAccountExecute();
        updateAccounts();
        updateGroups();
        updateChats();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
        String dateStr = null;
        if (listNews.getValue() != null && listNews.getValue().size() != 0) //для системы кэша
            dateStr = sdf.format(listNews.getValue().get(listNews.getValue().size() - 1).getDate());
        else
            dateStr = sdf.format(new Date(System.currentTimeMillis()));

        updateNewsFeed(dateStr, new StandardCallback() { @Override  public void call() { } });

        updateEvents();
        updateClasses();
    }

    /** updates */
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

    public void getMyAccountExecute() {
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
    }

    public void updateAccounts() {
        //Update accounts
        Repository.getInstance().getAccounts()
                .enqueue(new Callback<List<PrivateAccountDTO>>() {
                             @Override
                             public void onResponse(Call<List<PrivateAccountDTO>> call, Response<List<PrivateAccountDTO>> response) {

                                 if (response.body() != null && response.body().size() != 0) {
                                     List<PrivateAccountDTO> listDto = response.body();

                                     listAccounts.clear();

                                     for (PrivateAccountDTO dto : listDto)
                                         listAccounts.add(convertToAccount(dto));

                                     statusInfo.AccountListGot = true;
                                 }
                             }

                             @Override
                             public void onFailure(Call<List<PrivateAccountDTO>> call, Throwable t) {

                             }
                         }
                );
    }

    public void updateGroups() {
        //
        Repository.getInstance().getGroups().enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                if (response.body() == null)
                    return;
                listGroups = response.body();
                statusInfo.GroupsListGot = true;
                buildGroups();


                //clubs
                Repository.getInstance().getClubs().enqueue(new Callback<List<Club>>() {
                    @Override
                    public void onResponse(Call<List<Club>> call, Response<List<Club>> response) {
                        if (response.body() != null && response.body().size() != 0) {
                            statusInfo.ClubListGot = true;
                            List<Club> clubList = response.body();
                            buildClubs(clubList);
                            listClubs.postValue(clubList);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Club>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                System.err.println("Failure ошибка групп " + t.getMessage());
            }
        });
    }

    public void updateChats() {
        //
        Repository.getInstance().getChats().enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                if (response.body() != null) {
                    List<Chat> cChats = response.body();
                    statusInfo.ChatsListGot = true;
                    buildChats(cChats);
                    listChats.postValue(cChats);
                }
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                System.err.println("Failure ошибка чатов " + t.getMessage());
            }
        });
    }

    public void updateEvents() {
        Repository.getInstance().getEvents().enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.body() != null && response.body().size() != 0) {
                    listEvents.postValue(response.body());
                    statusInfo.EventsListGot = true;
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {

            }
        });
    }

    public void updateClasses() {
        Repository.getInstance().getClasses().enqueue(new Callback<List<Class>>() {
            @Override
            public void onResponse(Call<List<Class>> call, Response<List<Class>> response) {

                if (response.body() != null && response.body().size() != 0) {
                    statusInfo.TeacherListClassesGot = true;
                    buildClasses(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Class>> call, Throwable t) {

            }
        });
    }

    public void updateNewsFeed(String date, StandardCallback callback) {
        Repository.getInstance().getNewsFeed(date).enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {

                if (response.body() != null && response.body().size() != 0) {
                    List<News> newsList_ = response.body();
                    buildNews(newsList_);

                    if (listNews.getValue() == null)
                        listNews.setValue(newsList_);
                    else if (listNews.getValue().size() == 0)
                        listNews.setValue(newsList_);
                    else {
                        List<News> cNews = listNews.getValue();
                        cNews.addAll(newsList_);
                        listNews.setValue(cNews);
                    }
                    callback.call();
                    statusInfo.NewsListGot = true;
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {

            }
        });
    }

    //--------send

    public void sendMessage(CreateMessageDTO message) {
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

    public void addNews(CreateNewsDTO news) {
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



    /** Lazy метод. Подгружаем картинки для новости и храним их в кэше*/
    public void getNewsImagesLazy(News news, ImageLoadCallback imageLoadCallback) {

        if (news == null) return;
        if (news.getImages().getValue() == null)
            news.getImages().setValue(new ArrayList<>());
        news.getImages().getValue().clear();

        Repository.getInstance().getCountImages(news.getId(), "news_image").enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Integer count = response.body();

                if (count == null) return;

                for (int j = 0; j < count; j++) {
                    if (news.getGroup() == null)
                        return;
                    String author = news.getGroup().getName();

                    String UUID = GeneratorUUID.getInstance().generateUUIDForNews( //генерация uuid
                            DateUtil.getInstance().getDateToForm(news.getDate()), author);

                    Repository.getInstance().getImage(UUID, "news_image").enqueue(new Callback<Image>() {
                        @Override
                        public void onResponse(Call<Image> call, Response<Image> response) {
                            if (response.body() != null) {
                                NewsImage newsImage = new NewsImage();
                                newsImage.setImage(response.body());
                                newsImage.setUuid(UUID);

                                List<NewsImage> imageList = news.getImages().getValue();
                                imageList.add(newsImage);
                                news.getImages().postValue(imageList);
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
        if (message.getImages().getValue() == null)
            message.getImages().setValue(new ArrayList<>());
        message.getImages().getValue().clear();

        Repository.getInstance().getCountImages(message.getId(), "message_image").enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Integer count = response.body();
                if (count == null) return;
                if (count == 0)
                    message.setNoImages(true);

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
                                messageImage.setUuid(UUID);

                                List<MessageImage> messageImages = message.getImages().getValue();
                                messageImages.add(messageImage);
                                message.getImages().postValue(messageImages);

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

    //--------------------
    public void getTopStudentsFaculty(TopLoadCallback callback) {
        Repository.getInstance().getTopStudentsFaculty().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.body() != null) {
                    topsUsers.topsUsersFaculty = response.body();
                    callback.LoadTop(topsUsers);
                    statusInfo.TopsListUsersUniversityGot = true;
                }
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
                if (response.body() != null) {
                    topsUsers.topsUsersUniversity = response.body();
                    callback.LoadTop(topsUsers);
                    statusInfo.TopsListUsersFacultyGot = true;
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

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

    private void buildChats(List<Chat> listChats) {
        if (listChats != null) {
            for (Chat chat : listChats)
                for (Message message : chat.getMessages()) {
                    message.setChat(chat);
                    message.setChatId((long)chat.getId());
                    message.setImages(new MutableLiveData<>());
                    message.getImages().postValue(new ArrayList<>());
                }
        }
    }

    private void buildNews(List<News> newsList) {
        for (News news : newsList) {
            news.setImages(new MutableLiveData<>());

            Group group = listGroups.stream().filter(x->x.getId() == news.getIdGroup()).findAny().orElse(null);
            if (group != null)
                news.setGroup(group);
        }
    }

    private void buildGroups() {
        if (listGroups != null) {

            //разделим клубы и стандартные группы
            for (int j = 0; j < listGroups.size(); j++)
                if (listGroups.get(j).getType().equals("club")) {
                    groupClubs.add(listGroups.get(j));
                    listGroups.remove(listGroups.get(j));
                }

            //распределим аккаунты на каждую группу и группу на каждый аккаунт и также для новостей
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

                    //распределим группу на каждую новость если значения group новости = null
                    List<News> listNews_ = listNews.getValue();
                    if (listNews_ != null) {
                        listNews_.stream().forEach(x -> {
                            if (x.getIdGroup().equals(group.getId()) && x.getGroup() == null)
                                x.setGroup(group);
                        });
                    }
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

    public void buildClubs(List<Club> clubList) {


        if (!groupClubs.isEmpty()) {

            for (Club club : clubList) {
                for (Group group : groupClubs) //определяем группу для клуба
                    if (club.getId_group() == group.getId()) {
                        club.setGroup(group);
                        group.setAccounts(new ArrayList<>());
                        break;
                    }

                for (Account account : listAccounts) {
                    if (club.getId_leader() == account.getId())
                        club.setLeader(account);

                    if (account.getId_club() == club.getId())
                        club.getGroup().getAccounts().add(account);
                }


            }

        }
    }

    public String getJwtKey() {
        return jwtKey;
    }
}


