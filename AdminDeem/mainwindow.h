#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include "Faculty.h"
#include "server.h"

QT_BEGIN_NAMESPACE
namespace Ui { class MainWindow; }
QT_END_NAMESPACE

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

    void init(Server *value);
    void setCourse(Course *value);
    void setFacultyName(const QString &value);
    void setFaculties(const QList<Faculty *> &value);

public slots:
    void updateListAccounts(QList<Account*> accounts);
    void updateListGroups(QList<Group*> groups);

private slots:
    void on_sendScore_button_1_clicked();
    void on_Release_Event_Button_clicked();

    void on_expelStudent_clicked();

    void on_transferStudent_clicked();

    void on_listGroups_currentRowChanged(int currentRow);

    void on_listAccounts_currentRowChanged(int currentRow);

    void on_listGroupsForTransfer_currentRowChanged(int currentRow);

    void on_createGroup_button_clicked();

    void on_createAccount_button_clicked();

    void resultDepartureForm(bool error);

private:
    Ui::MainWindow *ui;
    Server* server;

    Course* course;
    QList<Account*> currentAccounts;
    QList<Faculty*> faculties;

    Account* accountChoose;
    Group* groupChoose;
    Group* groupChooseForTransfer;

    QString facultyName;

    bool(*fn)();
    std::function<void()> func;
};
#endif // MAINWINDOW_H
