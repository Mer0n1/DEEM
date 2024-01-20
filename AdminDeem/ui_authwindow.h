/********************************************************************************
** Form generated from reading UI file 'authwindow.ui'
**
** Created by: Qt User Interface Compiler version 5.14.2
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_AUTHWINDOW_H
#define UI_AUTHWINDOW_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QDialog>
#include <QtWidgets/QLabel>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QTextEdit>

QT_BEGIN_NAMESPACE

class Ui_authwindow
{
public:
    QPushButton *auth;
    QTextEdit *textEdit_login;
    QTextEdit *textEdit_password;
    QLabel *label;
    QLabel *label_2;
    QLabel *label_3;

    void setupUi(QDialog *authwindow)
    {
        if (authwindow->objectName().isEmpty())
            authwindow->setObjectName(QString::fromUtf8("authwindow"));
        authwindow->resize(389, 300);
        auth = new QPushButton(authwindow);
        auth->setObjectName(QString::fromUtf8("auth"));
        auth->setGeometry(QRect(90, 210, 211, 41));
        textEdit_login = new QTextEdit(authwindow);
        textEdit_login->setObjectName(QString::fromUtf8("textEdit_login"));
        textEdit_login->setGeometry(QRect(80, 90, 231, 31));
        textEdit_password = new QTextEdit(authwindow);
        textEdit_password->setObjectName(QString::fromUtf8("textEdit_password"));
        textEdit_password->setGeometry(QRect(80, 140, 231, 31));
        label = new QLabel(authwindow);
        label->setObjectName(QString::fromUtf8("label"));
        label->setGeometry(QRect(90, 10, 221, 51));
        label->setLayoutDirection(Qt::LeftToRight);
        label_2 = new QLabel(authwindow);
        label_2->setObjectName(QString::fromUtf8("label_2"));
        label_2->setGeometry(QRect(20, 90, 71, 21));
        label_3 = new QLabel(authwindow);
        label_3->setObjectName(QString::fromUtf8("label_3"));
        label_3->setGeometry(QRect(20, 140, 71, 21));

        retranslateUi(authwindow);

        QMetaObject::connectSlotsByName(authwindow);
    } // setupUi

    void retranslateUi(QDialog *authwindow)
    {
        authwindow->setWindowTitle(QCoreApplication::translate("authwindow", "Dialog", nullptr));
        auth->setText(QCoreApplication::translate("authwindow", "\320\220\320\262\321\202\320\276\321\200\320\270\320\267\320\270\321\200\320\276\320\262\320\260\321\202\321\214\321\201\321\217", nullptr));
        label->setText(QCoreApplication::translate("authwindow", "<html><head/><body><p align=\"center\"><span style=\" font-size:12pt; font-weight:600;\">AdminDeem</span></p></body></html>", nullptr));
        label_2->setText(QCoreApplication::translate("authwindow", "<html><head/><body><p><span style=\" font-size:10pt;\">\320\233\320\276\320\263\320\270\320\275</span></p></body></html>", nullptr));
        label_3->setText(QCoreApplication::translate("authwindow", "<html><head/><body><p><span style=\" font-size:10pt;\">\320\237\320\260\321\200\320\276\320\273\321\214</span></p></body></html>", nullptr));
    } // retranslateUi

};

namespace Ui {
    class authwindow: public Ui_authwindow {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_AUTHWINDOW_H
