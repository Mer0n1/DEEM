#ifndef MAININFO_H
#define MAININFO_H

#include "Faculty.h"
#include "mainwindow.h"
#include "networkdownloader.h"

#include <QDialog>

namespace Ui {
class maininfo;
}

class maininfo : public QDialog
{
    Q_OBJECT

public:
    explicit maininfo(QWidget *parent = nullptr);
    ~maininfo();

    void init(Server *server, MainWindow* mw, NetworkDownloader* networkdown);

public slots:
    void updateListFaculties(QList<Faculty*> faculties);

private slots:
    void on_list_faculties_currentRowChanged(int currentRow);
    void on_list_courses_currentRowChanged(int currentRow);
    void on_listGroups_currentRowChanged(int currentRow);
    void on_listAccounts_currentRowChanged(int currentRow);
    void on_pushButton_clicked();

private:
    void clearInfo();
    void updateListGroups();

private:
    Ui::maininfo *ui;
    Server* server;
    MainWindow* mainwindow;
    NetworkDownloader* networkdown;

    QList<Faculty*> faculties;

    int facultyChangedRow;
    int courseChangedRow;
    QString courseChangedName;
    QString facultyChangedName;
    Group* groupChanged;

    QList<Account*> currentAccounts;
    QList<Group*>   currentGroups;
};

#endif // MAININFO_H
