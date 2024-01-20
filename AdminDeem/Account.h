#include <QString>

#ifndef ACCOUNT_H
#define ACCOUNT_H

struct Account {

    long id;
    QString username;
    QString password;

    QString name;
    QString surname;
    QString fathername;
    QString role;
    int score;
    long group_id;
};
#endif // ACCOUNT_H
