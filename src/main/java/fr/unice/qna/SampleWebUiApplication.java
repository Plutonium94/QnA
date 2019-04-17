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

import java.util.List;
import java.util.ArrayList;


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

			System.out.println("===============");


			System.out.println(u1);
			System.out.println(u2);

			System.out.println("===============");

			Question q1 = new Question("Number of continents","How many continents are there in the world ?", "Geography");
			Question q2 = new Question("Photosynthesis", "What is Photosynthesis ?", "Biology");
			Question q3 = new Question("Object Oriented Languages", "What are some Object Oriented Languages ?", "Software");

			q1.setAuthor(u1);
			q2.setAuthor(u2);
			q3.setAuthor(u1);

			for(Question q : new Question[]{q1,q2,q3}) {
				qrep.save(q);
			}

			Question qang = new Question("Alternatives to Angular","Are there any front-end mvc frameworks other than Angular", "OO");
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



		};
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleWebUiApplication.class, args);
	}

}
