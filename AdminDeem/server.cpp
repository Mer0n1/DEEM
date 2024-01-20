#include "server.h"

#include <QHttpPart>
#include <QJsonParseError>
#include <QNetworkReply>

Server::Server()
{
}

Server::~Server() {

}

void Server::expelStudent(ExclusionForm form)
{
    QString json = "{\"idStudent\":" + QString::number(form.idStudent) + ",\"date\":\"" + form.date.toString(Qt::ISODate) + "\",\"description\":\"" + form.description + "\"}";
    QNetworkAccessManager* mngr = sendData(json.toUtf8(), QUrl("http://localhost:8081/admin/expelStudent"), TypeMetod::post);
    connect(mngr, SIGNAL(finished(QNetworkReply*)), this, SLOT(getResponse(QNetworkReply*)));
}

void Server::transferStudent(TransferForm form)
{
    QString json = "{\"id_group\":" + QString::number(form.id_group) + ",\"idStudent\":" + QString::number(form.idStudent) + ",\"description\":\"" + form.description + "\"}";
    sendData(json.toUtf8(), QUrl("http://localhost:8081/admin/createGroup"), TypeMetod::post);
}

void Server::createAccount(EnrollmentForm form)
{
    QString json = "{\"faculty\":\"" + form.faculty + "\",\"department\":\"" + form.department + "\",\"past_school\":\"" + form.past_school + "\","
                     "\"account\":{\"username\":\"" + form.account.username + "\",\"password\":\"" + form.account.password + "\",\"name\":\"" +
                    form.account.name + "\",\"surname\":\"" + form.account.surname + "\",\"fathername\":\"" + form.account.fathername + "\",\"role\":\"" +
                    form.account.role + "\",\"score\":" + QString::number(form.account.score) + ",\"group_id\":" + QString::number(form.account.group_id) + "}}";
    sendData(json.toUtf8(), QUrl("http://localhost:8081/admin/createAccount"), TypeMetod::post);
}

void Server::createGroup(GroupCreationForm form)
{
    QString json = "{\"department\":\"EPCFG\",\"group\":{\"name\":\"" + form.group.name + "\",\"course\":" +
                    QString::number(form.group.course) + ",\"faculty\":\"" + form.group.faculty + "\",\"date_create\":\"" + form.group.date_create.toString(Qt::ISODate) + "\"}}";
    sendData(json.toUtf8(), QUrl("http://localhost:8081/admin/createGroup"), TypeMetod::post);
}

void Server::sendScore(DepartureForm form)
{
    QString json = "{\"idAccount\":" + QString::number(form.idAccount) + ",\"score\":" + QString::number(form.score) + "}";

    QNetworkAccessManager* mngr = sendData(json.toUtf8(), QUrl("http://localhost:8081/admin/sendScore"), TypeMetod::post);
    this->connect(mngr, SIGNAL(finished(QNetworkReply*)), SLOT(getResponse(QNetworkReply*)));
}

void Server::releaseEvent(Event event)
{
    QString json = "{\"name\":\"" + event.name + "\",\"type\":\"" + event.type + "\",\"course\":" + QString::number(event.course) +
            ",\"faculty\":\"" + event.faculty + "\",\"idGroup\":" + QString::number(event.idGroup) + ",\"start_date\":\"" + event.start_date.toString(Qt::ISODate)
            + "\",\"description\":\"" + event.description + "\",\"publication_date\":\"" + event.publication_date.toString(Qt::ISODate) + "\",\"idGroup\":" + QString::number(event.idGroup) + "}";
    sendData(json.toUtf8(), QUrl("http://localhost:8081/admin/releaseEvent"), TypeMetod::post);
}



QNetworkAccessManager* Server::sendData(QByteArray data, QUrl url, TypeMetod type)
{
    QNetworkRequest request(url);
    QNetworkAccessManager *mngr = new QNetworkAccessManager(this);
    request.setRawHeader( "Authorization", "Bearer " + jwtKey.toUtf8());
    request.setHeader(QNetworkRequest::ContentTypeHeader, "application/json"); //x-www-form-urlencoded /json

    if (type == TypeMetod::get)
        mngr->get(request);
    else if (type == TypeMetod::post)
        mngr->post(request, data);
    return mngr;
}

QNetworkAccessManager* Server::sendData(QUrl url, Server::TypeMetod type)
{
    return sendData("", url, type);
}

void Server::Auth(QString jwt)
{
    this->jwtKey = jwt;
}

void Server::getResponse(QNetworkReply *reply)
{
    emit resultDepartureForm(reply->error() != reply->NoError);

    qDebug() << reply->error() << " " << (reply->error() == reply->NoError);
}





