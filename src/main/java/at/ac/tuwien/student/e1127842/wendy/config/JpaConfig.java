package at.ac.tuwien.student.e1127842.wendy.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({"at.ac.tuwien.student.e1127842.wendy.domain", "at.ac.tuwien.student.e1127842.wendyw.repository"})
@EnableJpaRepositories(basePackages = {"at.ac.tuwien.student.e1127842.wendy.repository"})
@EnableTransactionManagement
public class JpaConfig {
}