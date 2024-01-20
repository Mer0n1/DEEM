#include "networkdownloader.h"

#include <QThread>

NetworkDownloader::NetworkDownloader()
{
    //test
    Faculty* epf = new struct Faculty();
    Faculty* epc = new struct Faculty();

    epf->name = "EPF";
    epc->name = "EPC";

    faculties.append(epf);
    faculties.append(epc);
    //

    AccountsGot = false;
    GroupsGot   = false;
}

void NetworkDownloader::init(Server *server)
{
    this->server = server;
}

QList<Faculty *> NetworkDownloader::getFaculties() const
{
    return faculties;
}

void NetworkDownloader::setFaculties(const QList<Faculty *> &value)
{
    faculties = value;
}

void NetworkDownloader::UpdateListAccounts()
{
    QNetworkAccessManager* mngr = server->sendData(QUrl("http://localhost:8081/getAuth/getAccounts"), Server::get);
    this->connect(mngr, SIGNAL(finished(QNetworkReply*)), SLOT(getAccounts(QNetworkReply*)));
}

void NetworkDownloader::UpdateListGroups()
{
    QNetworkAccessManager* mngr = server->sendData(QUrl("http://localhost:8081/group/getAllGroups"), Server::get);
    this->connect(mngr, SIGNAL(finished(QNetworkReply*)), SLOT(getGroups(QNetworkReply*)));
}

void NetworkDownloader::UpdateListEvents()
{
    QNetworkAccessManager* mngr = server->sendData(QUrl("http://localhost:8081/event/getAllEvents"), Server::get);
    this->connect(mngr, SIGNAL(finished(QNetworkReply*)), SLOT(getEvents(QNetworkReply*)));
}

void NetworkDownloader::startDownload()
{
    UpdateListGroups();
    UpdateListAccounts();
    UpdateListEvents();
}

void NetworkDownloader::getAccounts(QNetworkReply *reply)
{
    QJsonArray jsonArray = QJsonDocument::fromJson(reply->readAll()).array();

    foreach (const QJsonValue & value, jsonArray) {
        QJsonObject obj = value.toObject();

        Account *account = new Account();
        account->id       = obj.value("id").toInt();
        account->username = obj.value("username").toString();
        account->role     = obj.value("role").toString();
        account->score    = obj.value("score").toInt();
        account->name     = obj.value("name").toString();
        account->surname  = obj.value("surname").toString();
        account->group_id = obj.value("group_id").toInt();
        account->password = obj.value("password").toString();
        account->fathername = obj.value("fathername").toString();

        allAccounts.append(account);
    }
    AccountsGot = true;
    organize();
}

void NetworkDownloader::getGroups(QNetworkReply *reply)
{
    QJsonArray jsonArray = QJsonDocument::fromJson(reply->readAll()).array();

    foreach (const QJsonValue & value, jsonArray) {
        QJsonObject obj = value.toObject();

        Group* group = new Group();
        group->id          = obj.value("id").toInt();
        group->name        = obj.value("name").toString();
        group->course      = obj.value("course").toInt();
        group->chat_id     = obj.value("chat_id").toInt();
        group->faculty     = obj.value("faculty").toString();
        group->date_create = QDateTime::fromString(obj.value("date_create").toString(),Qt::ISODate);
        group->score       = obj.value("score").toInt();

        QJsonArray users = obj["users"].toArray();
        foreach (const QJsonValue & value1, users)
            group->users.append(value1.toInt());

        allGroups.append(group);
    }
    GroupsGot = true;
    organize();

}

void NetworkDownloader::getEvents(QNetworkReply *reply)
{
    QJsonArray jsonArray = QJsonDocument::fromJson(reply->readAll()).array();

    foreach (const QJsonValue & value, jsonArray) {
        QJsonObject obj = value.toObject();

        Event *event = new Event();
        event->id       = obj.value("id").toInt();
        event->name     = obj.value("name").toString();
        event->type     = obj.value("type").toString();
        event->faculty  = obj.value("faculty").toString();
        event->idGroup  = obj.value("idGroup").toInt();
        event->course   = obj.value("course").toInt();
        event->start_date = QDateTime::fromString(obj.value("start_date").toString(),Qt::ISODate);
        event->description = obj.value("description").toString();
        event->publication_date = QDateTime::fromString(obj.value("publication_date").toString(),Qt::ISODate);

        allEvents.append(event);
    }
    EventsGot = true;
    organize();
}

void NetworkDownloader::organize()
{
    if (AccountsGot && GroupsGot && EventsGot) {
        //распределим аккаунты на группы
        for (Group* group : allGroups)
            for (Account* account : allAccounts)
                for (int j = 0; j < group->users.size(); j++)
                    if (group->users[j] == account->id) {
                        group->accounts.append(account);
                        break;
                    }

        //распределим курсы на каждый факультет
        QSet<int> courses;
        for (Group* group : allGroups)
            courses.insert(group->course);

        for (int i : courses)
            for (Faculty* f : faculties) {
                Course* course = new Course();
                course->course = i;
                f->courses.append(course);
            }


        //группу на каждый курс
        for (Group* group : allGroups)
            for (Faculty* f : faculties)
                if (group->faculty == f->name) {

                    //распределяем курсы
                    for (Course* c : f->courses)
                        if (group->course == c->course) {
                            c->groups.append(group);
                            break;
                        }

                    //f->groups.append(group);
                    break;
                }


        for (Faculty* f : faculties) {
            //удалим ненужные курсы
            for (Course* c : f->courses)
                if (c->groups.size() == 0)
                   f->courses.removeOne(c);

            //сортировка по иерархии
            sortByHierarchy(f);

            //распределим экзамены и разные события
            for (Event* e : allEvents) {
                if (f->name == e->faculty)
                    for (Course* c : f->courses)
                        if (c->course == e->course) {
                            c->events.append(e);
                            break;
                        }
            }
        }

        //обновляем данные для всего проекта
        emit updateFaculties(faculties);

    }
}

void NetworkDownloader::sortByHierarchy(Faculty *fac)
{
    for (Course* c : fac->courses) {

        QList<Group*>& groups = c->groups;

        for (int j = 0; j < groups.size(); j++)
            for (int c = 0; c < groups.size(); c++)
                if (groups[j]->score > groups[c]->score) {
                    Group* group = groups[j];
                    groups[j] = groups[c];
                    groups[c] = group;
                }

    }
}
