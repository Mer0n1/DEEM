/********************************************************************************
** Form generated from reading UI file 'maininfo.ui'
**
** Created by: Qt User Interface Compiler version 5.14.2
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_MAININFO_H
#define UI_MAININFO_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QDialog>
#include <QtWidgets/QLabel>
#include <QtWidgets/QListWidget>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QTextBrowser>

QT_BEGIN_NAMESPACE

class Ui_maininfo
{
public:
    QListWidget *list_faculties;
    QListWidget *listGroups;
    QListWidget *listAccounts;
    QPushButton *pushButton;
    QLabel *label;
    QLabel *name_group;
    QLabel *label_2;
    QTextBrowser *textBrowser_currentEvent;
    QLabel *number;
    QLabel *group_score;
    QTextBrowser *textBrowser;
    QLabel *name_student;
    QLabel *score_student;
    QListWidget *list_courses;

    void setupUi(QDialog *maininfo)
    {
        if (maininfo->objectName().isEmpty())
            maininfo->setObjectName(QString::fromUtf8("maininfo"));
        maininfo->resize(1117, 603);
        list_faculties = new QListWidget(maininfo);
        list_faculties->setObjectName(QString::fromUtf8("list_faculties"));
        list_faculties->setGeometry(QRect(20, 10, 101, 571));
        listGroups = new QListWidget(maininfo);
        listGroups->setObjectName(QString::fromUtf8("listGroups"));
        listGroups->setGeometry(QRect(1035, 10, 71, 571));
        listAccounts = new QListWidget(maininfo);
        listAccounts->setObjectName(QString::fromUtf8("listAccounts"));
        listAccounts->setGeometry(QRect(825, 10, 191, 571));
        pushButton = new QPushButton(maininfo);
        pushButton->setObjectName(QString::fromUtf8("pushButton"));
        pushButton->setGeometry(QRect(624, 10, 191, 41));
        label = new QLabel(maininfo);
        label->setObjectName(QString::fromUtf8("label"));
        label->setGeometry(QRect(250, 20, 141, 41));
        name_group = new QLabel(maininfo);
        name_group->setObjectName(QString::fromUtf8("name_group"));
        name_group->setGeometry(QRect(450, 20, 121, 51));
        label_2 = new QLabel(maininfo);
        label_2->setObjectName(QString::fromUtf8("label_2"));
        label_2->setGeometry(QRect(210, 150, 211, 41));
        textBrowser_currentEvent = new QTextBrowser(maininfo);
        textBrowser_currentEvent->setObjectName(QString::fromUtf8("textBrowser_currentEvent"));
        textBrowser_currentEvent->setGeometry(QRect(210, 190, 211, 391));
        number = new QLabel(maininfo);
        number->setObjectName(QString::fromUtf8("number"));
        number->setGeometry(QRect(450, 100, 121, 21));
        group_score = new QLabel(maininfo);
        group_score->setObjectName(QString::fromUtf8("group_score"));
        group_score->setGeometry(QRect(450, 150, 121, 31));
        textBrowser = new QTextBrowser(maininfo);
        textBrowser->setObjectName(QString::fromUtf8("textBrowser"));
        textBrowser->setGeometry(QRect(430, 190, 171, 391));
        name_student = new QLabel(maininfo);
        name_student->setObjectName(QString::fromUtf8("name_student"));
        name_student->setGeometry(QRect(640, 80, 141, 31));
        score_student = new QLabel(maininfo);
        score_student->setObjectName(QString::fromUtf8("score_student"));
        score_student->setGeometry(QRect(640, 130, 131, 41));
        list_courses = new QListWidget(maininfo);
        list_courses->setObjectName(QString::fromUtf8("list_courses"));
        list_courses->setGeometry(QRect(130, 10, 71, 571));

        retranslateUi(maininfo);

        QMetaObject::connectSlotsByName(maininfo);
    } // setupUi

    void retranslateUi(QDialog *maininfo)
    {
        maininfo->setWindowTitle(QCoreApplication::translate("maininfo", "Dialog", nullptr));
        pushButton->setText(QCoreApplication::translate("maininfo", "\320\222\321\213\320\261\321\200\320\260\321\202\321\214", nullptr));
        label->setText(QCoreApplication::translate("maininfo", "<html><head/><body><p align=\"center\"><span style=\" font-size:11pt;\">\320\244\320\260\320\272\321\203\320\273\321\214\321\202\320\265\321\202</span></p></body></html>", nullptr));
        name_group->setText(QCoreApplication::translate("maininfo", "<html><head/><body><p align=\"center\"><br/></p></body></html>", nullptr));
        label_2->setText(QCoreApplication::translate("maininfo", "<html><head/><body><p align=\"center\"><span style=\" font-size:11pt;\">\320\242\320\265\320\272\321\203\321\210\320\270\320\271 \321\215\320\272\320\267\320\260\320\274\320\265\320\275</span></p></body></html>", nullptr));
        number->setText(QCoreApplication::translate("maininfo", "<html><head/><body><p align=\"center\"><br/></p></body></html>", nullptr));
        group_score->setText(QCoreApplication::translate("maininfo", "<html><head/><body><p align=\"center\"><br/></p></body></html>", nullptr));
        name_student->setText(QCoreApplication::translate("maininfo", "<html><head/><body><p align=\"center\"><br/></p></body></html>", nullptr));
        score_student->setText(QString());
    } // retranslateUi

};

namespace Ui {
    class maininfo: public Ui_maininfo {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_MAININFO_H
