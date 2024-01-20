#include <QDate>
#include <QString>

#ifndef EXCLUSIONFORM_H
#define EXCLUSIONFORM_H



struct ExclusionForm {
    QString description;
    QDateTime date;
    long idStudent;
};
#endif // EXCLUSIONFORM_H
