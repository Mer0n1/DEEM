#include "authwindow.h"
#include "maininfo.h"

#include <QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);

    MainWindow* w = new MainWindow();
    maininfo* minfo = new maininfo();
    Server* server = new Server();
    authwindow* auth = new authwindow();
    NetworkDownloader* networkdown = new NetworkDownloader();

    networkdown->init(server);
    w->init(server);
    minfo->init(server, w, networkdown);
    auth->init(minfo, server, networkdown);

    auth->show();

    return a.exec();
}
