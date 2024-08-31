package com.example.restful.api;

public class ServerStatusInfo {
    private boolean AuthServiceWorked;
    private boolean ExamServiceWorked;
    private boolean ImageServiceWorked;
    private boolean NewsServiceWorked;
    private boolean MessengerServiceWorked;
    private boolean GroupServiceWorked;

    protected boolean AccountListGot;
    protected boolean EventsListGot;
    protected boolean NewsListGot;
    protected boolean ChatsListGot;
    protected boolean GroupsListGot;
    protected boolean TopsListUsersFacultyGot;
    protected boolean TopsListUsersUniversityGot;
    protected boolean TeacherListClassesGot;
    protected boolean ClubListGot;

    public ServerStatusInfo() {
        AccountListGot = false;
        EventsListGot  = false;
        NewsListGot    = false;
        ChatsListGot   = false;
        GroupsListGot  = false;
        TopsListUsersFacultyGot     = false;
        TopsListUsersUniversityGot  = false;
        TeacherListClassesGot       = false;
        ClubListGot                 = false;
    }

    public boolean isAccountListGot() {
        return AccountListGot;
    }

    public boolean isEventsListGot() {
        return EventsListGot;
    }

    public boolean isNewsListGot() {
        return NewsListGot;
    }

    public boolean isChatsListGot() {
        return ChatsListGot;
    }

    public boolean isGroupsListGot() {
        return GroupsListGot;
    }

    public boolean isAuthServiceWorked() {
        return AuthServiceWorked;
    }

    public boolean isExamServiceWorked() {
        return ExamServiceWorked;
    }

    public boolean isImageServiceWorked() {
        return ImageServiceWorked;
    }

    public boolean isNewsServiceWorked() {
        return NewsServiceWorked;
    }

    public boolean isMessengerServiceWorked() {
        return MessengerServiceWorked;
    }

    public boolean isGroupServiceWorked() {
        return GroupServiceWorked;
    }

    public boolean isTopsListUsersFacultyGot() {
        return TopsListUsersFacultyGot;
    }

    public boolean isTopsListUsersUniversityGot() {
        return TopsListUsersUniversityGot;
    }

    public boolean isTeacherListClassesGot() { return TeacherListClassesGot; }

    public boolean isClubListGot() {
        return ClubListGot;
    }


}
