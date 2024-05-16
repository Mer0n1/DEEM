package com.example.statistics_service.controllers;

import com.example.statistics_service.config.PersonDetails;
import com.example.statistics_service.models.Account;
import com.example.statistics_service.models.StatisticAccount;
import com.example.statistics_service.models.SubmissionForm;
import com.example.statistics_service.services.RestTemplateService;
import com.example.statistics_service.services.ScoreHistoryService;
import com.example.statistics_service.services.UpdatesHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.statistics_service.models.RecordDateUpdate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/statistic")
public class StatisticController {

    @Autowired
    private RestTemplateService restTemplateService;
    @Autowired
    private UpdatesHistoryService updatesHistoryService;
    @Autowired
    private ScoreHistoryService scoreHistoryService;


    @Scheduled(cron = "0 0 0 * * ?") //0 0 0 * *
    public void runEvery24Hours() {
        try {
            RecordDateUpdate record = updatesHistoryService.getLastRecord(); //время последнего обновления

            if (record == null)
                record = new RecordDateUpdate(new Date(System.currentTimeMillis()));
            else
                if (record.getDate().getMonth() == (new Date(System.currentTimeMillis())).getMonth())
                    throw new Exception("Последняя дата записи в БД равна текущей дате месяца. Рассинхронизация");

            List<SubmissionForm> forms = restTemplateService.getSubmissionForms(
                    record.getDate().getYear() + 1900, record.getDate().getMonth() + 1);
            List<Account> accounts = restTemplateService.getAccounts();
            List<StatisticAccount> statisticAccounts = new ArrayList<>();

            //создадим формы аккаунтов
            for (Account account : accounts)
                statisticAccounts.add(new StatisticAccount(account.getId()));

            //распределим формы баллов на каждый аккаунт
            for (SubmissionForm form : forms)
                for (StatisticAccount statisticAccount : statisticAccounts)
                    if (form.getIdAccount() == statisticAccount.getIdAccount()) {
                        statisticAccount.getForms().add(form);
                        break;
                    }

            //общий подсчет баллов за этот месяц
            for (StatisticAccount account : statisticAccounts) {
                for (SubmissionForm submissionForm : account.getForms())
                    account.addScore(submissionForm.getScore());
                account.setDate(new Date(System.currentTimeMillis()));
            }

            //после сохранение
            RecordDateUpdate recordDateUpdate = new RecordDateUpdate(new Date(System.currentTimeMillis()));
            updatesHistoryService.save(recordDateUpdate);

            //сохранить формы аккаунтов в качестве записей об очках накопленных в этот месяц
            scoreHistoryService.saveAll(statisticAccounts);


        } catch (Exception e) {
            System.err.println(e.getMessage()); //логирование
        }
    }

    @GetMapping(name = "/getMyStatistic")
    public StatisticAccount getMyStatistic(@AuthenticationPrincipal PersonDetails personDetails) {
        return scoreHistoryService.getStatistic(personDetails.getId());
    }

    @PreAuthorize("hasRole('HIGH')")
    @GetMapping(name = "/getMonthStatistics")
    public List<StatisticAccount> getMonthStatistics(@RequestParam("year") int year, @RequestParam("month") int month) {
        return scoreHistoryService.getMonthStatistic(year, month);
    }
}
