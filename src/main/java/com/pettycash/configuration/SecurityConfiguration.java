package com.pettycash.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter 
{
	
	@Autowired
	DataSource dataSource;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

	  auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery(
			"select name, password, enabled from \"public\".\"user\"  where name=?")
		.authoritiesByUsernameQuery(
	  		"select distinct name, rolename from user_role as ur "+
	  		"inner join \"public\".\"user\" as us on(us.id = ur.user_id) "+
	  		"inner join \"public\".\"role\" as rl on(rl.id = ur.role_id) "+
	  		"where us.name=?");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

	  http.authorizeRequests()
		.antMatchers("/custodio/**")
		.access("isAuthenticated()")
		//.access("hasRole('CUSTODIAN')")
		.and()
		  .formLogin().loginPage("/login").failureUrl("/login?error")
		  .usernameParameter("username").passwordParameter("password")
		.and()
		  //.logout().logoutSuccessUrl("/login?logout")
		  .logout().logoutSuccessUrl("/logedout")
		.and()
		  .exceptionHandling().accessDeniedPage("/403")
		  .and()	
			.csrf().disable();
	}
	
	
}