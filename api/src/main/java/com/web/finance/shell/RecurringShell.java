package com.web.finance.shell;

import com.web.finance.model.Investment;
import com.web.finance.model.Recurring;
import com.web.finance.model.User;
import com.web.finance.repository.RecurringRepository;
import com.web.finance.repository.UserRepository;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * These shell methods are designed to be used in conjuction with a service like cron to process expenses and
 * investments on a schedule. It is recommened to create 4 cron jobs. One for daily expenses, one for monthly,
 * one for yearly, and one for monthly investment interest calcuations. If this application is run via Docker,
 * it is recommeneded to run these cron jobs within the docker container itself.
 */
@ShellComponent
public class RecurringShell {
    private final UserRepository userRepository;

    public RecurringShell(RecurringRepository recurringRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ShellMethod(value="Process recurring requests", key="process-recurring")
    public String processRecurring(@ShellOption(help = "Frecuency of to processes. Must by DAILY, MONTHLY or YEARLY") String frequency) {
        List<User> users = userRepository.findAll();
        users.forEach(user -> {
            Set<Recurring> recurrings = user.getRecurrings();
            recurrings.stream().filter(recurring -> false)
                    .forEach(recurring -> {
                        recurring.getAccount() .setBalance(recurring.getAccount()
                                    .getBalance().add(recurring.getAmount()));
                    });
        });
        return "accounts succsfully processed";
    }

    @ShellMethod(value="Process monthly investment interest", key="process-investments")
    public String processInvesments() {
        List<User> users = userRepository.findAll();
        users.forEach(user -> {
            Set<Investment> recurrings = user.getInvestments();
            recurrings.forEach(i -> { i.setBalance(i.getBalance().multiply(i.getInterestRate()));
                    });
        });
        return "accounts succsfully processed";
    }
}
