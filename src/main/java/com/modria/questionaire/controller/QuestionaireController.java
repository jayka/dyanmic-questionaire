package com.modria.questionaire.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.modria.questionaire.model.Answer;
import com.modria.questionaire.model.AnswersForm;
import com.modria.questionaire.model.Element;

@Controller
@RequestMapping("/show")
public class QuestionaireController {

	private static SessionFactory sessionFactory = null; 
    private static ServiceRegistry serviceRegistry = null; 
    
    public QuestionaireController() throws HibernateException { 
        Configuration configuration = new Configuration(); 
        configuration.configure(); 
         
        Properties properties = configuration.getProperties();
        
        serviceRegistry = new ServiceRegistryBuilder().applySettings(properties).buildServiceRegistry();         
        sessionFactory = configuration.buildSessionFactory(serviceRegistry); 
         
    }
    
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView getQuestionaire() {
		
		Session session = sessionFactory.openSession();
		// question list
		List<Element> qlist = session.createQuery("from Element where masterId=1 and parentId is null").list();
		
		// saved answers.. simple map for now.. enhance to pojo later
		Map<Integer, String> aMap = getSavedAnswers(session);
		
		ModelAndView mv = new ModelAndView("showMessage");
		mv.addObject("qlist", qlist);
		mv.addObject("aMap", aMap);
		session.close();
		return mv;
	}
	
	private Map<Integer, String> getSavedAnswers(Session session) {
		
		List<Answer> alist = session.createQuery("from Answer where masterId=1").list();
		Map<Integer, String> aMap = new HashMap<Integer, String>();
		
		if(alist != null) {
			for(Answer answer : alist) {
				aMap.put(answer.getId(), answer.getValue());
			}
		}
		return aMap;
	}

	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView saveQuestionaire(@ModelAttribute("answersForm") AnswersForm answersForm) {
		Collection<Answer> answers = getAnswers(answersForm);
		boolean success = upsertAnswers(answers);
		ModelAndView mv = new ModelAndView("showMessage");
		mv.addObject("saved", success);
		return mv;
	}

	/**
	 * Upsert answers
	 */
	private boolean upsertAnswers(Collection<Answer> answers) {
		
		Session session = sessionFactory.openSession();;
		Transaction tx = session.beginTransaction();
		try {
			Query query = session.createQuery("delete from Answer where masterId=1");
			int executeUpdate = query.executeUpdate();
			System.out.println("Rows deleted:"+executeUpdate);
			tx.commit();
		} catch(Throwable t) {
			tx.rollback();
		}
		
		tx = session.beginTransaction();
		try {
			for(Answer answer : answers)
				session.save(answer);
			
			tx.commit();
		} catch(Throwable t) {
			tx.rollback();
			return false;
		} finally {
			session.close();
		}
		
		return true;
	}

	private Collection<Answer> getAnswers(AnswersForm answersForm) {
		Map<String, String> answersMap = answersForm.getAnswersMap();
		List<Answer> answers = new ArrayList<Answer>();
		if(answersMap!=null) {
			for(Entry<String, String> entry : answersMap.entrySet()) {
				// only valid values answers are taken
				if(!StringUtils.isEmpty(entry.getValue())) {
					Answer answer = new Answer();
					answer.setMasterId(1);
					answer.setId(Integer.parseInt(entry.getKey()));
					answer.setValue(entry.getValue());
					answers.add(answer);
				}
			}
		}
		
		return answers;
	}

}
