package com.example.restful.api;

import com.example.restful.api.websocket.PushClient;
import com.example.restful.models.Account;
import com.example.restful.models.AuthRequest;
import com.example.restful.models.Chat;
import com.example.restful.models.DataImage;
import com.example.restful.models.Event;
import com.example.restful.models.Group;
import com.example.restful.models.IconImage;
import com.example.restful.models.Image;
import com.example.restful.models.ImageLoadCallback;
import com.example.restful.models.Message;
import com.example.restful.models.MessageImage;
import com.example.restful.models.News;
import com.example.restful.models.NewsImage;
import com.example.restful.utils.DateUtil;
import com.example.restful.utils.GeneratorUUID;
import com.sun.imageio.plugins.common.ImageUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.xml.crypto.Data;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sun.jvm.hotspot.utilities.BitMap;


public class APIManager {

    private static APIManager manager;
    private static PushClient pushClient;

    public List<Account> listAccounts;
    public List<Group> listGroups;
    public List<Chat> listChats;
    public List<News> listNews;
    public List<Event> listEvents;

    public Account myAccount;

    private String jwtKey;

    private APIManager() {
    }

    public static APIManager getManager() {
        if (manager == null) {
            manager = new APIManager();
            pushClient = new PushClient();
        }
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
        Call<Account> str = Repository.getInstance().getMyAccount(Handler.getToken());
        Callback<Account> cl = new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.body() != null)
                System.out.println(response.body().getSurname());
                myAccount = response.body();
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {

            }
        };

        str.enqueue(cl);
    }


    public void UpdateData() {

        final Call<Account> str = Repository.getInstance().getMyAccount();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Response<Account> stri = null;
                try {
                    stri = str.execute();
                    myAccount = stri.body();
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
                .enqueue(new Callback<List<Account>>() {
                    @Override
                    public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                        listAccounts = response.body();

                        //загрузим иконки аккаунтов
                        for (Account account : listAccounts)
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
                                    });
                    }

                    @Override
                    public void onFailure(Call<List<Account>> call, Throwable t) {

                    }
                }
        );


        //
        Repository.getInstance().getGroups().enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                listGroups = response.body();
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
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                System.err.println("Failure ошибка чатов " + t.getMessage());
            }
        });


        Repository.getInstance().getNews(myAccount.getGroup().getFaculty()).enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                listNews = response.body();
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {

            }
        });


        Repository.getInstance().getEvents().enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                listEvents = response.body();
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {

            }
        });
    }

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


    /***** ImageService ******/

    public void GetImage(String UUID, String type, ImageLoadCallback imageLoadCallback) {
        Repository.getInstance().getImage(UUID, type).enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {
                System.err.println("RESPONSE IMAGE");
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

    public void addNewsImages(List<NewsImage> imgs) {
        Repository.getInstance().addNewsImages(imgs).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void addMessageImages(List<MessageImage> imgs) {
        Repository.getInstance().addMessageImages(imgs).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    /** Lazy метод. Подгружаем картинки для новости и храним их в кэше*/
    public void getNewsImages(News news, ImageLoadCallback imageLoadCallback) {

        if (news.getListImg() == null)
            news.setListImg(new ArrayList<>());
        news.getListImg().clear();

        Repository.getInstance().getCountImages(news.getId(), "news_image").enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Integer count = response.body();

                if (count == null) return;

                for (int j = 0; j < count; j++) {
                    Group group = listGroups.stream().filter(x->x.getId()==news.getIdGroup()).findAny().orElse(null);
                    if (group == null) return;
                    String author = group.getName();
                    System.err.println("++++ " + author);

                    String UUID = GeneratorUUID.getInstance().generateUUIDForNews(
                            DateUtil.getInstance().getDateToForm(news.getDate()), author);

                    Repository.getInstance().getImage(UUID, "news_image").enqueue(new Callback<Image>() {
                        @Override
                        public void onResponse(Call<Image> call, Response<Image> response) {
                            System.err.println(response.body());
                            if (response.body() != null) {
                                System.err.println(response.body().getImgEncode());
                                news.getListImg().add(response.body());
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

    //такие же lazy методы с иконками и сообщениями
}


