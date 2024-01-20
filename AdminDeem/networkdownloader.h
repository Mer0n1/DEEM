#ifndef NETWORKDOWNLOADER_H
#define NETWORKDOWNLOADER_H


#include "Faculty.h"
#include "server.h"

#include <QObject>
#include <QNetworkReply>


class NetworkDownloader : public QObject
{
    Q_OBJECT
public:
    NetworkDownloader();

    void init(Server* server);

    QList<Faculty *> getFaculties() const;
    void setFaculties(const QList<Faculty *> &value);

    void UpdateListAccounts();
    void UpdateListGroups();
    void UpdateListEvents();

    void startDownload();
    void sortByHierarchy(Faculty* fac);

public slots:
    void getAccounts(QNetworkReply *reply);
    void getGroups  (QNetworkReply *reply);
    void getEvents  (QNetworkReply *reply);
private:
    void organize();
signals:
    void updateFaculties(QList<Faculty*> faculties);
private:
    QList<Faculty*> faculties;
    Server* server;

    QList<Account*> allAccounts;
    QList<Group*> allGroups;
    QList<Event*> allEvents;

    bool AccountsGot;
    bool GroupsGot;
    bool EventsGot;
};

#endif // NETWORKDOWNLOADER_H
