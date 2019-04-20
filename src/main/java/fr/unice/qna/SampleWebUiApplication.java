/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.unice.qna;

import fr.unice.qna.persistence.*;
import fr.unice.qna.ui.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;


/*@Configuration
@EnableAutoConfiguration
@ComponentScan*/
@SpringBootApplication
public class SampleWebUiApplication {

	@Bean
	public MessageRepository messageRepository() {
		return new InMemoryMessageRespository();
	}

	@Bean
	public Converter<String, Message> messageConverter() {
		return new Converter<String, Message>() {
			@Override
			public Message convert(String id) {
				return messageRepository().findMessage(Long.valueOf(id));
			}
		};
	}

	@Bean
	public CommandLineRunner insertSampleData(QuestionRepository qrep, TagRepository trep, QnAUserRepository urep) {
		return (String[] args) -> {

			trep.save(new Tag("Physics"));
			trep.save(new Tag("Sports"));
			trep.save(new Tag("Volleyball"));

			QnAUser u1 = new QnAUser("Joe","joe@joe.com","apple");
			QnAUser u2 = new QnAUser("Pierre","pierre@joe.com","orange");


			u1 = urep.save(u1);
			u2 = urep.save(u2);

			saveSampleUsers(urep);

			System.out.println("===============");


			System.out.println(u1);
			System.out.println(u2);

			System.out.println("===============");

			Question q1 = new Question("Number of continents","How many continents are there in the world ?");
			q1.addTag("Geography");
			Question q2 = new Question("Photosynthesis", "What is Photosynthesis ?");
			q2.addTag("Biology");
			Question q3 = new Question("Object Oriented Languages", "What are some Object Oriented Languages ?");
			q3.addTag("Software");

			q1.setAuthor(u1);
			q2.setAuthor(u2);
			q3.setAuthor(u1);

			for(Question q : new Question[]{q1,q2,q3}) {
				qrep.save(q);
			}

			Question qang = new Question("Alternatives to Angular","Are there any front-end mvc frameworks other than Angular");
			qang.addTag("OO");
			Answer a1 = new Answer("VueJS"); a1.setAuthor(u1);
			Answer a2 = new Answer("ReactJS"); a2.setAuthor(u2);
			List<Answer> ans = new ArrayList<Answer>();
			ans.add(a1);
			ans.add(a2);
			qang.setAnswers(ans);
			qang.setAuthor(u2);
			System.out.println(qang);
			System.out.println(qang.getAuthor());
			System.out.println(qrep.save(qang));

			System.out.println(saveSampleQuestions(qrep) + " sample questions saved");

		};
	}

	public int saveSampleUsers(QnAUserRepository urep) {
		int res = 0;
		try {
			File f = ResourceUtils.getFile("classpath:sample-data/users.csv");
			BufferedReader br = new BufferedReader(new FileReader(f));
			for(String line = br.readLine(); line != null; line = br.readLine()) {
				String[] lineSplit = splitComma(line);
				// String[] lineSplit = StringUtils.delimitedListToStringArray(line, ",", " ");
				QnAUser user = new QnAUser(lineSplit[0], lineSplit[1], lineSplit[2]);
				user = urep.save(user);
				System.out.println(user);
				++res;
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		return res;
	}

	public int saveSampleTags(TagRepository trep) {
		int res = 0;
		try {
			File f = ResourceUtils.getFile("classpath:sample-data/tags.csv");
			BufferedReader br = new BufferedReader(new FileReader(f));
			for(String tagName = br.readLine(); tagName != null; tagName = br.readLine()) {
				trep.save(new Tag(tagName));
				res++;
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		return res;
	} 

	public int saveSampleQuestions(QuestionRepository qrep) {
		int res = 0;
		try {
			File f = ResourceUtils.getFile("classpath:sample-data/questions.csv");
			BufferedReader br = new BufferedReader(new FileReader(f));
			for(String line = br.readLine(); line != null; line = br.readLine()) {
				String[] lineSplit = splitComma(line);
				if(lineSplit.length >= 3) {
					Question q = qrep.create(lineSplit[0], lineSplit[1], lineSplit[2]);
					if(q != null) {
						if(lineSplit.length >= 4) {
							SortedSet<Tag> tags = new TreeSet<Tag>();
							for(String tagName : lineSplit[3].split("\\|")) {
								if(!tagName.trim().equals("")) {
									tags.add(new Tag(tagName));
								}
							}
							q.setTags(tags);
							q = qrep.save(q);
						}
						res++;
					}
				}
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		return res;
	}

	private static String[] splitComma(String line) {
		String[] res = StringUtils.commaDelimitedListToStringArray(line);
		for(int i = 0; i < res.length; i++) {
			res[i] = res[i].trim();
		}
		return res;
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleWebUiApplication.class, args);
	}

}
