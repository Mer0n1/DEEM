#include "maininfo.h"
#include "ui_maininfo.h"

maininfo::maininfo(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::maininfo)
{
    ui->setupUi(this);

    courseChangedRow  = -1;
    facultyChangedRow = -1;
}

maininfo::~maininfo()
{
    delete ui;
}


void maininfo::init(Server *server, MainWindow* mw, NetworkDownloader* networkdown)
{
    this->server = server;
    this->mainwindow = mw;
    this->networkdown = networkdown;

    connect(networkdown, SIGNAL(updateFaculties(QList<Faculty*>)), this, SLOT(updateListFaculties(QList<Faculty*>)));
}


void maininfo::updateListFaculties(QList<Faculty *> faculties)
{
    this->faculties = faculties;

    for (Faculty* faculty : faculties)
        ui->list_faculties->addItem(faculty->name);
}

void maininfo::on_list_faculties_currentRowChanged(int currentRow)
{
    if (currentRow == -1) return;

    facultyChangedRow = currentRow;
    facultyChangedName = ui->list_faculties->item(currentRow)->text();

    //сброс информации
    ui->list_courses->clear();
    ui->listAccounts->clear();
    ui->listGroups->clear();
    courseChangedName.clear();
    ui->textBrowser_currentEvent->clear();
    courseChangedRow = -1;
    clearInfo();

    //обновим список курсов на данном факультете
    for (Course* course : faculties[currentRow]->courses)
        ui->list_courses->addItem(QString::number(course->course));

}

void maininfo::on_list_courses_currentRowChanged(int currentRow)
{
    if (currentRow == -1) return;

    courseChangedRow = currentRow;
    courseChangedName = ui->list_courses->item(currentRow)->text();

    ui->listAccounts->clear();
    ui->listGroups->clear();

    if (faculties[facultyChangedRow]->courses[courseChangedRow]->events.size() != 0)
        ui->textBrowser_currentEvent->setText(faculties[facultyChangedRow]->courses[courseChangedRow]->events[0]->name);

    updateListGroups();

}

void maininfo::updateListGroups()
{
    ui->listGroups->clear();
    currentGroups.clear();
    ui->listAccounts->clear();
    currentAccounts.clear();
    clearInfo();

    for (Group* group : faculties[facultyChangedRow]->courses[courseChangedRow]->groups)
        if (group->course == courseChangedName.toInt() && group->faculty == facultyChangedName)
            currentGroups.append(group);

    for (Group* group : currentGroups)
        ui->listGroups->addItem(group->name);
}

void maininfo::on_listGroups_currentRowChanged(int currentRow)
{
    if (currentRow < 0) return;

    ui->listAccounts->clear();
    currentAccounts.clear();

    groupChanged = currentGroups[currentRow];

    for (Account* account : groupChanged->accounts)
        if (groupChanged->id == account->group_id) {
            ui->listAccounts->addItem(account->username);
            currentAccounts.append(account);
        }


    ui->name_group->setText(groupChanged->name);
    ui->group_score->setText(QString::number(groupChanged->score));
    ui->number->setText(QString::number(currentRow+1));
}

void maininfo::on_listAccounts_currentRowChanged(int currentRow)
{
    if (currentRow >= 0) {
        ui->name_student->setText(currentAccounts[currentRow]->username);
        ui->score_student->setText(QString::number(currentAccounts[currentRow]->score));
    }
}


void maininfo::clearInfo()
{
    ui->name_student->setText("");
    ui->score_student->setText("");
    ui->name_group->setText("");
    ui->group_score->setText("");
    ui->number->setText("");
}


void maininfo::on_pushButton_clicked()
{
    mainwindow->setCourse(faculties[facultyChangedRow]->courses[courseChangedRow]);
    mainwindow->updateListGroups(currentGroups);
    mainwindow->setFacultyName(faculties[facultyChangedRow]->name);
    mainwindow->setFaculties(faculties);

    mainwindow->show();
}
