package pl.edu.uj.kognitywistyka.admin.aboutproject.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import pl.edu.uj.kognitywistyka.admin.aboutproject.model.Report;


public class ReportDaoImpl extends HibernateDaoSupport
	implements ReportDao{

	
	public void addReport(Report report) {
		getHibernateTemplate().save(report);
		
	}

	@SuppressWarnings("unchecked")
	public List<Report> findAllReports() {
		return getHibernateTemplate().find("from Report");
	}
	


}