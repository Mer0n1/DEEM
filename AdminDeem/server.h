#ifndef SERVER_H
#define SERVER_H
#include "DepartureForm.h"
#include "EnrollmentForm.h"
#include "Event.h"
#include "ExclusionForm.h"
#include "GroupCreationForm.h"
#include "TransferForm.h"

#include <QMainWindow>
#include <QTimer>
#include <QEventLoop>
#include <QNetworkAccessManager>
#include <QJsonDocument>
#include <QJsonValue>
#include <QJsonArray>
#include <QJsonObject>

class Server : public QObject
{
    Q_OBJECT
public:
    Server();
    ~Server();

    enum TypeMetod { post, get };

    //post
    void expelStudent(ExclusionForm form);
    void transferStudent(TransferForm form);
    void createAccount(EnrollmentForm form);
    void createGroup(GroupCreationForm form);
    void sendScore(DepartureForm form);
    void releaseEvent(Event event);

public:
    QNetworkAccessManager* sendData(QByteArray data, QUrl url, TypeMetod type);
    QNetworkAccessManager* sendData(QUrl url, TypeMetod type);

public slots:
    void Auth(QString jwt);

private slots:
    void getResponse(QNetworkReply* reply);
signals:
    void resultDepartureForm(bool error);
private:
    QString jwtKey;

};

#endif // SERVER_H
