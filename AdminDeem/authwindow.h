#ifndef AUTHWINDOW_H
#define AUTHWINDOW_H

#include "maininfo.h"
#include "networkdownloader.h"

#include <QDialog>
#include <QNetworkReply>

namespace Ui {
class authwindow;
}

class authwindow : public QDialog
{
    Q_OBJECT

public:
    explicit authwindow(QWidget *parent = nullptr);
    ~authwindow();


    void init(maininfo *minfo, Server* server, NetworkDownloader* networkdown);

private slots:
    void on_auth_clicked();

    void getAnswerAuthFromServer(QNetworkReply *reply);

private:
    Ui::authwindow *ui;

    Server* server;
    maininfo* minfo;
    NetworkDownloader* networkdown;
};

#endif // AUTHWINDOW_H
