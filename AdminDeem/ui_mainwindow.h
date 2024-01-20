/********************************************************************************
** Form generated from reading UI file 'mainwindow.ui'
**
** Created by: Qt User Interface Compiler version 5.14.2
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_MAINWINDOW_H
#define UI_MAINWINDOW_H

#include <QtCore/QVariant>
#include <QtWidgets/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QComboBox>
#include <QtWidgets/QDateTimeEdit>
#include <QtWidgets/QLabel>
#include <QtWidgets/QListView>
#include <QtWidgets/QListWidget>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QTextEdit>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_MainWindow
{
public:
    QAction *action;
    QAction *action_2;
    QWidget *centralwidget;
    QListWidget *listAccounts;
    QPushButton *expelStudent;
    QTextEdit *descriptrion_1;
    QListWidget *listGroups;
    QPushButton *transferStudent;
    QTextEdit *score_text;
    QPushButton *sendScore_button_1;
    QLabel *label;
    QPushButton *Release_Event_Button;
    QTextEdit *event_name;
    QTextEdit *description_2;
    QListView *listView;
    QListWidget *listGroupsForTransfer;
    QLabel *label_2;
    QLabel *label_3;
    QTextEdit *course_group;
    QTextEdit *nameGroup;
    QPushButton *createGroup_button;
    QLabel *label_4;
    QLabel *label_5;
    QLabel *label_6;
    QTextEdit *school_account;
    QTextEdit *score_account;
    QTextEdit *password_account;
    QTextEdit *username_account;
    QTextEdit *name_student;
    QTextEdit *fathername_student;
    QTextEdit *surname_student;
    QPushButton *createAccount_button;
    QLabel *label_7;
    QLabel *label_8;
    QLabel *label_9;
    QLabel *label_10;
    QLabel *label_11;
    QLabel *label_12;
    QLabel *label_13;
    QLabel *label_14;
    QLabel *label_15;
    QLabel *label_16;
    QLabel *label_17;
    QLabel *label_18;
    QLabel *label_19;
    QLabel *label_20;
    QComboBox *role_account;
    QComboBox *faculty_by_creater_account;
    QComboBox *faculty_group;
    QDateTimeEdit *dateTimeEdit;
    QMenuBar *menubar;
    QStatusBar *statusbar;

    void setupUi(QMainWindow *MainWindow)
    {
        if (MainWindow->objectName().isEmpty())
            MainWindow->setObjectName(QString::fromUtf8("MainWindow"));
        MainWindow->resize(1099, 772);
        action = new QAction(MainWindow);
        action->setObjectName(QString::fromUtf8("action"));
        action_2 = new QAction(MainWindow);
        action_2->setObjectName(QString::fromUtf8("action_2"));
        centralwidget = new QWidget(MainWindow);
        centralwidget->setObjectName(QString::fromUtf8("centralwidget"));
        listAccounts = new QListWidget(centralwidget);
        listAccounts->setObjectName(QString::fromUtf8("listAccounts"));
        listAccounts->setGeometry(QRect(540, 10, 256, 531));
        expelStudent = new QPushButton(centralwidget);
        expelStudent->setObjectName(QString::fromUtf8("expelStudent"));
        expelStudent->setGeometry(QRect(270, 400, 81, 31));
        descriptrion_1 = new QTextEdit(centralwidget);
        descriptrion_1->setObjectName(QString::fromUtf8("descriptrion_1"));
        descriptrion_1->setGeometry(QRect(270, 40, 251, 351));
        listGroups = new QListWidget(centralwidget);
        listGroups->setObjectName(QString::fromUtf8("listGroups"));
        listGroups->setGeometry(QRect(800, 10, 141, 531));
        transferStudent = new QPushButton(centralwidget);
        transferStudent->setObjectName(QString::fromUtf8("transferStudent"));
        transferStudent->setGeometry(QRect(360, 400, 161, 31));
        score_text = new QTextEdit(centralwidget);
        score_text->setObjectName(QString::fromUtf8("score_text"));
        score_text->setGeometry(QRect(270, 460, 251, 31));
        sendScore_button_1 = new QPushButton(centralwidget);
        sendScore_button_1->setObjectName(QString::fromUtf8("sendScore_button_1"));
        sendScore_button_1->setGeometry(QRect(270, 500, 251, 41));
        label = new QLabel(centralwidget);
        label->setObjectName(QString::fromUtf8("label"));
        label->setGeometry(QRect(20, 20, 211, 21));
        Release_Event_Button = new QPushButton(centralwidget);
        Release_Event_Button->setObjectName(QString::fromUtf8("Release_Event_Button"));
        Release_Event_Button->setGeometry(QRect(20, 500, 211, 41));
        event_name = new QTextEdit(centralwidget);
        event_name->setObjectName(QString::fromUtf8("event_name"));
        event_name->setGeometry(QRect(20, 50, 211, 31));
        description_2 = new QTextEdit(centralwidget);
        description_2->setObjectName(QString::fromUtf8("description_2"));
        description_2->setGeometry(QRect(20, 90, 211, 121));
        listView = new QListView(centralwidget);
        listView->setObjectName(QString::fromUtf8("listView"));
        listView->setGeometry(QRect(20, 220, 211, 241));
        listGroupsForTransfer = new QListWidget(centralwidget);
        listGroupsForTransfer->setObjectName(QString::fromUtf8("listGroupsForTransfer"));
        listGroupsForTransfer->setGeometry(QRect(950, 70, 141, 471));
        label_2 = new QLabel(centralwidget);
        label_2->setObjectName(QString::fromUtf8("label_2"));
        label_2->setGeometry(QRect(950, 20, 141, 51));
        label_3 = new QLabel(centralwidget);
        label_3->setObjectName(QString::fromUtf8("label_3"));
        label_3->setGeometry(QRect(270, 10, 241, 21));
        course_group = new QTextEdit(centralwidget);
        course_group->setObjectName(QString::fromUtf8("course_group"));
        course_group->setGeometry(QRect(120, 570, 131, 31));
        nameGroup = new QTextEdit(centralwidget);
        nameGroup->setObjectName(QString::fromUtf8("nameGroup"));
        nameGroup->setGeometry(QRect(120, 610, 131, 31));
        createGroup_button = new QPushButton(centralwidget);
        createGroup_button->setObjectName(QString::fromUtf8("createGroup_button"));
        createGroup_button->setGeometry(QRect(20, 690, 231, 31));
        label_4 = new QLabel(centralwidget);
        label_4->setObjectName(QString::fromUtf8("label_4"));
        label_4->setGeometry(QRect(20, 570, 81, 31));
        label_5 = new QLabel(centralwidget);
        label_5->setObjectName(QString::fromUtf8("label_5"));
        label_5->setGeometry(QRect(20, 610, 91, 31));
        label_6 = new QLabel(centralwidget);
        label_6->setObjectName(QString::fromUtf8("label_6"));
        label_6->setGeometry(QRect(20, 650, 81, 31));
        school_account = new QTextEdit(centralwidget);
        school_account->setObjectName(QString::fromUtf8("school_account"));
        school_account->setGeometry(QRect(400, 610, 131, 31));
        score_account = new QTextEdit(centralwidget);
        score_account->setObjectName(QString::fromUtf8("score_account"));
        score_account->setGeometry(QRect(690, 650, 131, 31));
        password_account = new QTextEdit(centralwidget);
        password_account->setObjectName(QString::fromUtf8("password_account"));
        password_account->setGeometry(QRect(690, 570, 131, 31));
        username_account = new QTextEdit(centralwidget);
        username_account->setObjectName(QString::fromUtf8("username_account"));
        username_account->setGeometry(QRect(400, 650, 131, 31));
        name_student = new QTextEdit(centralwidget);
        name_student->setObjectName(QString::fromUtf8("name_student"));
        name_student->setGeometry(QRect(940, 570, 131, 31));
        fathername_student = new QTextEdit(centralwidget);
        fathername_student->setObjectName(QString::fromUtf8("fathername_student"));
        fathername_student->setGeometry(QRect(940, 650, 131, 31));
        surname_student = new QTextEdit(centralwidget);
        surname_student->setObjectName(QString::fromUtf8("surname_student"));
        surname_student->setGeometry(QRect(940, 610, 131, 31));
        createAccount_button = new QPushButton(centralwidget);
        createAccount_button->setObjectName(QString::fromUtf8("createAccount_button"));
        createAccount_button->setGeometry(QRect(300, 690, 261, 31));
        label_7 = new QLabel(centralwidget);
        label_7->setObjectName(QString::fromUtf8("label_7"));
        label_7->setGeometry(QRect(286, 580, 101, 21));
        label_7->setStyleSheet(QString::fromUtf8(""));
        label_8 = new QLabel(centralwidget);
        label_8->setObjectName(QString::fromUtf8("label_8"));
        label_8->setGeometry(QRect(310, 620, 71, 21));
        label_9 = new QLabel(centralwidget);
        label_9->setObjectName(QString::fromUtf8("label_9"));
        label_9->setGeometry(QRect(310, 650, 71, 21));
        label_10 = new QLabel(centralwidget);
        label_10->setObjectName(QString::fromUtf8("label_10"));
        label_10->setGeometry(QRect(590, 580, 71, 21));
        label_11 = new QLabel(centralwidget);
        label_11->setObjectName(QString::fromUtf8("label_11"));
        label_11->setGeometry(QRect(850, 580, 71, 21));
        label_12 = new QLabel(centralwidget);
        label_12->setObjectName(QString::fromUtf8("label_12"));
        label_12->setGeometry(QRect(850, 610, 81, 21));
        label_13 = new QLabel(centralwidget);
        label_13->setObjectName(QString::fromUtf8("label_13"));
        label_13->setGeometry(QRect(850, 650, 71, 21));
        label_14 = new QLabel(centralwidget);
        label_14->setObjectName(QString::fromUtf8("label_14"));
        label_14->setGeometry(QRect(590, 620, 71, 21));
        label_15 = new QLabel(centralwidget);
        label_15->setObjectName(QString::fromUtf8("label_15"));
        label_15->setGeometry(QRect(590, 660, 71, 21));
        label_16 = new QLabel(centralwidget);
        label_16->setObjectName(QString::fromUtf8("label_16"));
        label_16->setGeometry(QRect(6, 12, 241, 541));
        label_16->setStyleSheet(QString::fromUtf8("border:2px solid black;"));
        label_17 = new QLabel(centralwidget);
        label_17->setObjectName(QString::fromUtf8("label_17"));
        label_17->setGeometry(QRect(260, 10, 271, 431));
        label_17->setStyleSheet(QString::fromUtf8("border: 2px solid black;"));
        label_18 = new QLabel(centralwidget);
        label_18->setObjectName(QString::fromUtf8("label_18"));
        label_18->setGeometry(QRect(260, 450, 271, 101));
        label_18->setStyleSheet(QString::fromUtf8("border: 2px solid black;"));
        label_19 = new QLabel(centralwidget);
        label_19->setObjectName(QString::fromUtf8("label_19"));
        label_19->setGeometry(QRect(10, 560, 251, 171));
        label_19->setStyleSheet(QString::fromUtf8("border: 2px solid black;"));
        label_20 = new QLabel(centralwidget);
        label_20->setObjectName(QString::fromUtf8("label_20"));
        label_20->setGeometry(QRect(270, 560, 821, 171));
        label_20->setStyleSheet(QString::fromUtf8("border: 2px solid black;"));
        role_account = new QComboBox(centralwidget);
        role_account->addItem(QString());
        role_account->addItem(QString());
        role_account->addItem(QString());
        role_account->setObjectName(QString::fromUtf8("role_account"));
        role_account->setGeometry(QRect(690, 610, 131, 31));
        faculty_by_creater_account = new QComboBox(centralwidget);
        faculty_by_creater_account->setObjectName(QString::fromUtf8("faculty_by_creater_account"));
        faculty_by_creater_account->setGeometry(QRect(400, 570, 131, 31));
        faculty_group = new QComboBox(centralwidget);
        faculty_group->setObjectName(QString::fromUtf8("faculty_group"));
        faculty_group->setGeometry(QRect(120, 650, 131, 31));
        dateTimeEdit = new QDateTimeEdit(centralwidget);
        dateTimeEdit->setObjectName(QString::fromUtf8("dateTimeEdit"));
        dateTimeEdit->setGeometry(QRect(20, 470, 211, 22));
        MainWindow->setCentralWidget(centralwidget);
        label_17->raise();
        label_16->raise();
        label_20->raise();
        label_19->raise();
        label_18->raise();
        listAccounts->raise();
        expelStudent->raise();
        descriptrion_1->raise();
        listGroups->raise();
        transferStudent->raise();
        score_text->raise();
        sendScore_button_1->raise();
        label->raise();
        Release_Event_Button->raise();
        event_name->raise();
        description_2->raise();
        listView->raise();
        listGroupsForTransfer->raise();
        label_2->raise();
        label_3->raise();
        course_group->raise();
        nameGroup->raise();
        createGroup_button->raise();
        label_4->raise();
        label_5->raise();
        label_6->raise();
        school_account->raise();
        score_account->raise();
        password_account->raise();
        username_account->raise();
        name_student->raise();
        fathername_student->raise();
        surname_student->raise();
        createAccount_button->raise();
        label_7->raise();
        label_8->raise();
        label_9->raise();
        label_10->raise();
        label_11->raise();
        label_12->raise();
        label_13->raise();
        label_14->raise();
        label_15->raise();
        role_account->raise();
        faculty_by_creater_account->raise();
        faculty_group->raise();
        dateTimeEdit->raise();
        menubar = new QMenuBar(MainWindow);
        menubar->setObjectName(QString::fromUtf8("menubar"));
        menubar->setGeometry(QRect(0, 0, 1099, 21));
        MainWindow->setMenuBar(menubar);
        statusbar = new QStatusBar(MainWindow);
        statusbar->setObjectName(QString::fromUtf8("statusbar"));
        MainWindow->setStatusBar(statusbar);

        retranslateUi(MainWindow);

        QMetaObject::connectSlotsByName(MainWindow);
    } // setupUi

    void retranslateUi(QMainWindow *MainWindow)
    {
        MainWindow->setWindowTitle(QCoreApplication::translate("MainWindow", "MainWindow", nullptr));
        action->setText(QCoreApplication::translate("MainWindow", "\320\244\320\276\321\200\320\274\320\260 \321\201\320\276\320\267\320\264\320\260\320\275\320\270\321\217 \320\263\321\200\321\203\320\277\320\277\321\213", nullptr));
        action_2->setText(QCoreApplication::translate("MainWindow", "\320\244\320\276\321\200\320\274\320\260 \321\201\320\276\320\267\320\264\320\260\320\275\320\270\321\217 \320\272\320\273\320\270\320\265\320\275\321\202\320\260", nullptr));
        expelStudent->setText(QCoreApplication::translate("MainWindow", "\320\230\321\201\320\272\320\273\321\216\321\207\320\270\321\202\321\214", nullptr));
        transferStudent->setText(QCoreApplication::translate("MainWindow", "\320\237\320\265\321\200\320\265\320\262\320\265\321\201\321\202\320\270 \320\262 \320\264\321\200\321\203\320\263\321\203\321\216 \320\263\321\200\321\203\320\277\320\277\321\203", nullptr));
        sendScore_button_1->setText(QCoreApplication::translate("MainWindow", "\320\236\321\202\320\277\321\200\320\260\320\262\320\270\321\202\321\214 \320\261\320\260\320\273\320\273\321\213", nullptr));
        label->setText(QCoreApplication::translate("MainWindow", "<html><head/><body><p align=\"center\"><span style=\" font-size:10pt; font-weight:600;\">\320\244\320\276\321\200\320\274\320\260 \320\262\321\213\320\277\321\203\321\201\320\272\320\260 \321\215\320\272\320\267\320\260\320\274\320\265\320\275\320\260</span></p></body></html>", nullptr));
        Release_Event_Button->setText(QCoreApplication::translate("MainWindow", "\320\227\320\260\320\277\321\203\321\201\321\202\320\270\321\202\321\214", nullptr));
        label_2->setText(QCoreApplication::translate("MainWindow", "<html><head/><body><p align=\"center\"><span style=\" font-size:10pt;\">\320\223\321\200\321\203\320\277\320\277\321\213 \320\277\320\265\321\200\320\265\320\262\320\276\320\264\320\260</span></p></body></html>", nullptr));
        label_3->setText(QCoreApplication::translate("MainWindow", "\320\236\320\277\320\270\321\201\320\260\320\275\320\270\320\265 (\320\270\321\201\320\272\320\273\321\216\321\207\320\265\320\275\320\270\321\217 \320\270 \320\277\320\265\321\200\320\265\320\262\320\276\320\264\320\260 \320\262 \320\263\321\200\321\203\320\277\320\277\321\203)", nullptr));
        createGroup_button->setText(QCoreApplication::translate("MainWindow", "\320\241\320\276\320\267\320\264\320\260\321\202\321\214 \320\263\321\200\321\203\320\277\320\277\321\203", nullptr));
        label_4->setText(QCoreApplication::translate("MainWindow", "<html><head/><body><p align=\"center\">\320\232\321\203\321\200\321\201</p></body></html>", nullptr));
        label_5->setText(QCoreApplication::translate("MainWindow", "<html><head/><body><p align=\"center\">\320\235\320\260\320\267\320\262\320\260\320\275\320\270\320\265 \320\263\321\200\321\203\320\277\320\277\321\213</p></body></html>", nullptr));
        label_6->setText(QCoreApplication::translate("MainWindow", "<html><head/><body><p align=\"center\">\320\244\320\260\320\272\321\203\320\273\321\214\321\202\320\265\321\202</p></body></html>", nullptr));
        createAccount_button->setText(QCoreApplication::translate("MainWindow", "\320\241\320\276\320\267\320\264\320\260\321\202\321\214 \320\260\320\272\320\272\320\260\321\203\320\275\321\202", nullptr));
        label_7->setText(QCoreApplication::translate("MainWindow", "<html><head/><body><p align=\"center\">\320\244\320\260\320\272\321\203\320\273\321\214\321\202\320\265\321\202 (\320\272\321\202\320\276 \321\201\320\276\320\267\320\264\320\260\320\265\321\202)</p></body></html>", nullptr));
        label_8->setText(QCoreApplication::translate("MainWindow", "\320\250\320\272\320\276\320\273\320\260", nullptr));
        label_9->setText(QCoreApplication::translate("MainWindow", "\320\235\320\270\320\272\320\275\320\265\320\271\320\274", nullptr));
        label_10->setText(QCoreApplication::translate("MainWindow", "\320\237\320\260\321\200\320\276\320\273\321\214", nullptr));
        label_11->setText(QCoreApplication::translate("MainWindow", "\320\230\320\274\321\217", nullptr));
        label_12->setText(QCoreApplication::translate("MainWindow", "\320\244\320\260\320\274\320\270\320\273\320\270\321\217", nullptr));
        label_13->setText(QCoreApplication::translate("MainWindow", "\320\236\321\202\321\207\320\265\321\201\321\202\320\262\320\276", nullptr));
        label_14->setText(QCoreApplication::translate("MainWindow", "\320\240\320\276\320\273\321\214", nullptr));
        label_15->setText(QCoreApplication::translate("MainWindow", "\320\236\321\207\320\272\320\270", nullptr));
        label_16->setText(QString());
        label_17->setText(QString());
        label_18->setText(QString());
        label_19->setText(QString());
        label_20->setText(QString());
        role_account->setItemText(0, QCoreApplication::translate("MainWindow", "\320\241\321\202\321\203\320\264\320\265\320\275\321\202", nullptr));
        role_account->setItemText(1, QCoreApplication::translate("MainWindow", "\320\243\321\207\320\270\321\202\320\265\320\273\321\214", nullptr));
        role_account->setItemText(2, QCoreApplication::translate("MainWindow", "\320\220\320\264\320\274\320\270\320\275\320\270\321\201\321\202\321\200\320\260\321\202\320\276\321\200", nullptr));

    } // retranslateUi

};

namespace Ui {
    class MainWindow: public Ui_MainWindow {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_MAINWINDOW_H
