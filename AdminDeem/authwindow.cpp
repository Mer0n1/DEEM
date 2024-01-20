#include "authwindow.h"
#include "ui_authwindow.h"

authwindow::authwindow(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::authwindow)
{
    ui->setupUi(this);
}

authwindow::~authwindow()
{
    delete ui;
}

void authwindow::init(maininfo *minfo, Server* server, NetworkDownloader* networkdown)
{
    this->minfo = minfo;
    this->server = server;
    this->networkdown = networkdown;
}

void authwindow::on_auth_clicked()
{
    QString json;
    json = "{\"username\":\"" + ui->textEdit_login->toPlainText() + "\",\"password\":\"" + ui->textEdit_password->toPlainText() + "\"}";
    //AuthRequest request;
    //request.username = ui->textEdit_login->toPlainText();
    //request.password = ui->textEdit_password->toPlainText();


    QNetworkAccessManager* mngr = server->sendData(json.toUtf8(), QUrl("http://localhost:8081/auth/login"), Server::TypeMetod::post);
    this->connect(mngr, SIGNAL(finished(QNetworkReply*)), SLOT(getAnswerAuthFromServer(QNetworkReply*)));
}

void authwindow::getAnswerAuthFromServer(QNetworkReply *reply)
{
    //обработать ответ
    QString jwt = reply->readAll();

    if (!jwt.isEmpty()) {
        //проверка на роль
        minfo->show();
        server->Auth(jwt);
        networkdown->startDownload();
        this->close();
    }
}
