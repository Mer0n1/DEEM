#include "mainwindow.h"
#include "ui_mainwindow.h"

#include <QMessageBox>

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    accountChoose = NULL;
    groupChoose   = NULL;
    groupChooseForTransfer = NULL;
    func = NULL;

}

MainWindow::~MainWindow()
{
    delete ui;
}


void MainWindow::updateListAccounts(QList<Account *> accounts)
{
    ui->listAccounts->clear();

    for (Account* account : accounts)
        ui->listAccounts->addItem(account->username);
}

void MainWindow::updateListGroups(QList<Group *> groups)
{
    ui->listGroups->clear();

    for (Group* group : groups) {
        ui->listGroups->addItem(group->name);
        ui->listGroupsForTransfer->addItem(group->name);
    }

}

void MainWindow::init(Server *value)
{
    this->server = value;

    this->connect(server, SIGNAL(resultDepartureForm(bool)), SLOT(resultDepartureForm(bool)));
}

void MainWindow::setCourse(Course *value)
{
    course = value;
}

void MainWindow::on_listGroups_currentRowChanged(int currentRow)
{
    if (currentRow < 0) return;

    accountChoose = NULL;
    currentAccounts = course->groups[currentRow]->accounts;
    updateListAccounts(currentAccounts);
}

void MainWindow::on_listAccounts_currentRowChanged(int currentRow)
{
    if (currentRow < 0) return;

    accountChoose = currentAccounts[currentRow];
}

void MainWindow::on_listGroupsForTransfer_currentRowChanged(int currentRow)
{
    if (currentRow < 0) return;
    groupChooseForTransfer = course->groups[currentRow];
}

void MainWindow::setFacultyName(const QString &value)
{
    facultyName = value;
}


void MainWindow::setFaculties(const QList<Faculty *> &value)
{
    faculties = value;

    for (Faculty* faculties : faculties) {
        ui->faculty_group->addItem(faculties->name);
        ui->faculty_by_creater_account->addItem(faculties->name);
    }
}

//-------------------------------------------------


void MainWindow::on_expelStudent_clicked() //не тестировано
{
    if (accountChoose != NULL) {
        if (ui->descriptrion_1->toPlainText().isEmpty()) {
            QMessageBox::critical(this, "", "Пожалуйста заполните описание и повторите");
            return;
        }

        ExclusionForm form;
        form.description = ui->descriptrion_1->toPlainText();
        form.idStudent = accountChoose->id;
        form.date = QDateTime::currentDateTime();

        server->expelStudent(form);

    } else {
        QMessageBox::critical(this, "", "Не выбран аккаунт");
    }
}

void MainWindow::on_transferStudent_clicked()
{
    if (accountChoose != NULL && /*groupChoose != NULL &&*/ groupChooseForTransfer != NULL) {
        if (ui->descriptrion_1->toPlainText().isEmpty()) {
            QMessageBox::critical(this, "", "Пожалуйста заполните описание и повторите");
            return;
        }

        TransferForm form;
        form.description = ui->descriptrion_1->toPlainText();
        form.idStudent   = accountChoose->id;
        form.id_group    = groupChooseForTransfer->id;

        server->transferStudent(form);

    } else {
        QMessageBox::critical(this, "", "Не выбрана группа для перевода или аккаунт");
    }

}

void MainWindow::on_sendScore_button_1_clicked()
{
    if (accountChoose != NULL) {
        if (ui->score_text->toPlainText().isEmpty()) {
            QMessageBox::critical(this, "", "Отсутствуем количество баллов");
            return;
        }

        QString str = ui->score_text->toPlainText();
        QString username = ui->listAccounts->currentItem()->text();

        DepartureForm form;
        form.score = str.toInt();
        form.idAccount = accountChoose->id;

        server->sendScore(form);

        /*QSharedPointer<Ui::MainWindow> uiPtr(ui);
        QSharedPointer<MainWindow> mainWindowPtr(this);
        func = [uiPtr]() { uiPtr->listAccounts->addItem("Testttt"); QMessageBox::information(mainWindowPtr, "Баллы отправлены");}; */

    } else {
        QMessageBox::critical(this, "", "Не выбран аккаунт");
    }
}

void MainWindow::on_Release_Event_Button_clicked()
{
    Event event;
    event.name = ui->event_name->toPlainText();
    event.type = "all";
    event.faculty = facultyName;
    event.course  = course->course;
    event.start_date = ui->dateTimeEdit->dateTime();
    event.publication_date = QDateTime::currentDateTime();
    event.description = ui->description_2->toPlainText();

    if (groupChoose != NULL)
        event.idGroup = groupChoose->id;

    server->releaseEvent(event);
}




void MainWindow::on_createGroup_button_clicked()
{
    GroupCreationForm form;
    form.group.name    = ui->nameGroup->toPlainText();
    form.group.course  = ui->course_group->toPlainText().toInt();
    form.group.faculty = ui->faculty_group->currentText();
    form.group.date_create = QDateTime::currentDateTime();

    if (form.group.name.isEmpty() || form.group.course == 0 || form.group.faculty.isEmpty()) {
        QMessageBox::critical(this, "", "Заполните все данные");
        return;
    }

    server->createGroup(form);
}

void MainWindow::on_createAccount_button_clicked()
{
    if (ui->faculty_by_creater_account->currentText().isEmpty() || ui->school_account->toPlainText().isEmpty() || ui->name_student->toPlainText().isEmpty() ||
        ui->role_account->currentText().isEmpty() || ui->surname_student->toPlainText().isEmpty() || ui->password_account->toPlainText().isEmpty() ||
        ui->username_account->toPlainText().isEmpty() || ui->fathername_student->toPlainText().isEmpty()) {
        QMessageBox::critical(this, "", "Заполните все данные");
        return;
    } else if (groupChooseForTransfer == NULL) {
        QMessageBox::critical(this, "", "Выберите группу для зачисления");
        return;
    }

    EnrollmentForm form;
    form.department         = "EPUI";
    form.faculty            = ui->faculty_by_creater_account->currentText();
    form.past_school        = ui->school_account->toPlainText();
    form.account.name       = ui->name_student->toPlainText();
    form.account.score      = ui->score_account->toPlainText().toInt();
    form.account.surname    = ui->surname_student->toPlainText();
    form.account.password   = ui->password_account->toPlainText();
    form.account.username   = ui->username_account->toPlainText();
    form.account.fathername = ui->fathername_student->toPlainText();
    form.account.group_id   = groupChooseForTransfer->id;

    QString role = ui->role_account->currentText();
    if (role == "Админ")
        role = "ROLE_ADMIN";
    else if (role == "Учитель")
        role = "ROLE_TEACHER";
    else
        role = "ROLE_STUDENT";
    form.account.role = role;


    server->createAccount(form);
}

void MainWindow::resultDepartureForm(bool error)
{
    if (error)
        QMessageBox::critical(this, "", "Заполните все данные");
    else {
        if (func != NULL) {
            func();
            func = NULL;
        }
        QMessageBox::information(this, "", "Отправлено удачно");
    }
}


