package com.example.restful.api;

import androidx.lifecycle.MutableLiveData;

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
import com.example.restful.models.VideoCallback;
import com.example.restful.models.VideoMetadata;
import com.example.restful.models.curriculum.Class;
import com.example.restful.models.curriculum.DayliSchedule;
import com.example.restful.utils.DateUtil;
import com.example.restful.utils.GeneratorUUID;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private static Repository instance;
    private static ServerRepository serverRepository;
    private static CacheSystem cacheSystem;
    private static ModelMapper modelMapper;
    private static ServerStatusInfo statusInfo;

    protected volatile List<Account> listAccounts;
    protected volatile List<Group> listGroups;
    protected volatile List<Group> listGroupsOfFaculty;
    protected volatile List<Group> adminGroups;
    protected volatile MutableLiveData<List<Chat>> listChats;
    protected volatile MutableLiveData<List<News>> listNews;
    protected volatile MutableLiveData<List<Event>> listEvents;
    protected volatile MutableLiveData<List<Club>> listClubs;
    protected volatile List<DayliSchedule> dayliSchedules;
    protected volatile TopsUsers topsUsers;
    protected volatile List<Group> groupClubs;

    protected Account myAccount;

    private Repository() {
        modelMapper = new ModelMapper();
        serverRepository = ServerRepository.getInstance();
        cacheSystem = CacheSystem.getCacheSystem();
        statusInfo = new ServerStatusInfo();

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
        topsUsers = new TopsUsers();

        


    }

    public void initialize() {
        cacheSystem.loadAll();
        cacheSystem.observeData();
    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    protected ServerStatusInfo getStatusInfo() { return statusInfo; }

    protected void UpdateData() {

        getMyAccountExecute();
        updateAccounts();
        updateGroups();
        updateChats();
        updateNewsFeed(new StandardCallback() { @Override  public void call() { } });
        updateEvents();
        updateClasses();
    }

    /** updates */
    protected void getMyAccount() {
        Call<PublicAccountDTO> str = ServerRepository.getInstance().getMyAccount(Handler.getToken());
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

    protected void getMyAccountExecute() {
        final Call<PublicAccountDTO> str = ServerRepository.getInstance().getMyAccount();
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

    protected void updateAccounts() {
        //Update accounts
        ServerRepository.getInstance().getAccounts()
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

    protected void updateGroups() {
        //
        ServerRepository.getInstance().getGroups().enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                if (response.body() == null)
                    return;
                listGroups = response.body();
                statusInfo.GroupsListGot = true;
                buildGroups();


                //clubs
                ServerRepository.getInstance().getClubs().enqueue(new Callback<List<Club>>() {
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

    protected void updateChats() {

        ServerRepository.getInstance().getChats().enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                if (response.body() != null && response.body().size() != 0) {
                    List<Chat> cChats = response.body();
                    List<Chat> chats = listChats.getValue();

                    if (listChats.getValue() != null) { //Поскольку кэш уже загружает список чатов то добавляем только новые чаты
                        cChats.removeIf(item1 -> listChats.getValue().stream()
                                .anyMatch(item2 -> item1.getId() == item2.getId()));
                        chats.addAll(cChats);
                    } else
                        chats = cChats;

                    buildChats(cChats);
                    listChats.postValue(chats);

                    statusInfo.ChatsListGot = true;
                }
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                System.err.println("Failure ошибка чатов " + t.getMessage());
            }
        });
    }

    protected void updateEvents() {
        ServerRepository.getInstance().getEvents().enqueue(new Callback<List<Event>>() {
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

    protected void updateClasses() {
        ServerRepository.getInstance().getClasses().enqueue(new Callback<List<Class>>() {
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

    protected void updateNewsFeed(StandardCallback callback) {
        //Проверим по кэшу последнюю новость по дате
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
        String date = null;
        if (listNews.getValue() != null && listNews.getValue().size() != 0) //для системы кэша
            date = sdf.format(listNews.getValue().get(listNews.getValue().size() - 1).getDate());
        else
            date = sdf.format(new Date(System.currentTimeMillis()));


        //Отправим дату для актуализации данных по серверу
        ServerRepository.getInstance().getNewsFeed(date).enqueue(new Callback<List<News>>() {
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

    protected void updateMessagesFeed(StandardCallback callback, Long chatId) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
        String date = null;
        Chat chat = listChats.getValue().stream().filter(x->x.getId() == chatId).findAny().orElse(null);
        if (chat != null)
        if (chat.getMessages() != null && chat.getMessages().size() != 0) //для системы кэша
            date = sdf.format(chat.getMessages().get(0).getDate());
        else
            date = sdf.format(new Date(System.currentTimeMillis()));

        ServerRepository.getInstance().getMessagesFeed(date, chatId).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.body() != null && response.body().size() != 0) {
                    List<Message> newListMessages = response.body();
                    Collections.reverse(newListMessages);
                    buildMessages(newListMessages, chatId);
                    listChats.postValue(listChats.getValue());
                    callback.call();
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }

    /***** ImageService ******/

    protected void GetImage(String UUID, String type, ImageLoadCallback imageLoadCallback) {
        ServerRepository.getInstance().getImage(UUID, type).enqueue(new Callback<Image>() {
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


    /** Lazy метод. Подгружаем картинки для новости и храним их в кэше*/
    protected void getNewsImagesLazy(News news, ImageLoadCallback imageLoadCallback) {

        if (news == null)
            return;
        if (news.isCompleted())
            return;
        if (news.getImages().getValue() == null)
            news.getImages().setValue(new ArrayList<>());

        //Проверим кэш и если есть возьмем картинку
        String UUID = GeneratorUUID.getInstance().generateUUIDForNews( //генерация uuid
                DateUtil.getInstance().getDateToForm(news.getDate()), news.getGroup().getName());

        Image image = CacheSystem.getCacheSystem().getImageByUuid(UUID); //TODO or few

        if (image != null) {
            NewsImage newsImage = new NewsImage();
            newsImage.setImage(image);
            newsImage.setId_news(news.getId());
            newsImage.setUuid(UUID);
            news.getImages().getValue().add(newsImage);

            imageLoadCallback.onImageLoaded(image.getImgEncode());
            news.setCompleted(true);
            return;

        } else if (!news.isNoImages()) {

            //Обновляем с сервера
            news.getImages().getValue().clear();

            ServerRepository.getInstance().getCountImages(news.getId(), "news_image").enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    Integer count = response.body();

                    if (count == null) return;
                    if (count == 0)
                        news.setNoImages(true);

                    for (int j = 0; j < count; j++) {

                        ServerRepository.getInstance().getImage(UUID, "news_image").enqueue(new Callback<Image>() {
                            @Override
                            public void onResponse(Call<Image> call, Response<Image> response) {
                                if (response.body() != null) {
                                    NewsImage newsImage = new NewsImage();
                                    newsImage.setImage(response.body());
                                    newsImage.setUuid(UUID);

                                    List<NewsImage> imageList = news.getImages().getValue();
                                    imageList.add(newsImage);
                                    news.getImages().postValue(imageList);
                                    news.setCompleted(true);
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
    }

    protected void getIconImageLazy(Account account, ImageLoadCallback imageLoadCallback) {
        if (account == null) return;

        String username = account.getUsername();
        if (username == null || username.isEmpty())
            return;

        String UUID = GeneratorUUID.getInstance().generateUUIDForIcon(username);

        ServerRepository.getInstance().getImage(UUID, "profile_icon").enqueue(new Callback<Image>() {
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

    protected void getMessageImagesLazy(Message message, ImageLoadCallback imageLoadCallback) {
        if (message == null)
            return;
        if (message.isCompleted())
            return;
        if (message.getImages().getValue() == null)
            message.getImages().setValue(new ArrayList<>());
        message.getImages().getValue().clear();

        //Проверим кэш и если есть возьмем картинку
        Account account = APIManager.getManager().getListAccounts().stream().filter(x -> x.getId()
                == message.getAuthor()).findAny().orElse(null);

        String UUID = GeneratorUUID.getInstance().generateUUIDForMessage(
                DateUtil.getInstance().getDateToForm(message.getDate()), account.getUsername());

        Image image = CacheSystem.getCacheSystem().getImageByUuid(UUID);

        if (image != null) {
            MessageImage messageImage = new MessageImage();
            messageImage.setImage(image);
            messageImage.setId_message(message.getId());
            messageImage.setUuid(UUID);
            if (message.getImages().getValue() == null)
                message.getImages().setValue(new ArrayList<>());
            message.getImages().getValue().add(messageImage);

            imageLoadCallback.onImageLoaded(image.getImgEncode());
        } else if (!message.isNoImages()) {

            ServerRepository.getInstance().getCountImages(message.getId(), "message_image").enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    Integer count = response.body();
                    if (count == null) return;
                    if (count == 0)
                        message.setNoImages(true);

                    for (int j = 0; j < count; j++) {
                        Account account = listAccounts.stream().filter(x -> x.getId()
                                == message.getAuthor()).findAny().orElse(null);
                        if (account == null) return;
                        String author = account.getUsername();

                        String UUID = GeneratorUUID.getInstance().generateUUIDForMessage(
                                DateUtil.getInstance().getDateToForm(message.getDate()), author);

                        ServerRepository.getInstance().getImage(UUID, "message_image").enqueue(new Callback<Image>() {
                            @Override
                            public void onResponse(Call<Image> call, Response<Image> response) {
                                if (response.body() != null) {
                                    MessageImage messageImage = new MessageImage();
                                    messageImage.setImage(response.body());
                                    messageImage.setUuid(UUID);

                                    List<MessageImage> messageImages = message.getImages().getValue();
                                    messageImages.add(messageImage);
                                    message.getImages().postValue(messageImages);
                                    message.setCompleted(true);
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
    }

    //--------------------
    protected void getTopStudentsFaculty(TopLoadCallback callback) {
        ServerRepository.getInstance().getTopStudentsFaculty().enqueue(new Callback<List<String>>() {
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

    protected void getTopStudentsUniversity(TopLoadCallback callback) {
        ServerRepository.getInstance().getTopStudentsUniversity().enqueue(new Callback<List<String>>() {
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


    //--------------------VIDEO--------------------------
    protected void sendVideo(VideoMetadata videoMetadata) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("video/mp4"), videoMetadata.getVideo());
        MultipartBody.Part videoBody = MultipartBody.Part.createFormData("file", "video.mp4", requestFile);

        String metadataJson = "{\"id_dependency\":\"" + videoMetadata.getId_dependency() + "\",\"type\":\"" +
                videoMetadata.getType().toString() + "\",\"uuid\":\"" + videoMetadata.getUuid() + "\"}";
        RequestBody metadata = RequestBody.create(MediaType.parse("application/json"), metadataJson);

        ServerRepository.getInstance().sendVideo(videoBody, metadata).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

    protected void getVideo(VideoCallback callback, String VideoUUID) {
        ServerRepository.getInstance().getManifestVideo(VideoUUID).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null && !response.body().isEmpty())
                    callback.loadVideo(Base.BASE_URL + response.body().replace("%2F", "/"));
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }



    private Account convertToAccount(PrivateAccountDTO dto) {
        return modelMapper.map(dto, Account.class);
    }

    private Account convertToAccount(PublicAccountDTO account) {
        return modelMapper.map(account, Account.class);
    }

    private void buildMessages(List<Message> listMessages, Long chatId) {
        Chat chat = listChats.getValue().stream().filter(x->x.getId() == chatId)
                .findAny().orElse(null);

        if (chat != null) {
            for (Message message : listMessages) {
                message.setChat(chat);
                message.setChatId((long) chat.getId());
                message.setImages(new MutableLiveData<>());
                message.getImages().postValue(new ArrayList<>());
            }
            chat.getMessages().addAll(0, listMessages);
        }
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

}
