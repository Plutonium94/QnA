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

			qrep.save(new Question("Number of continents","How many continents are there in the world ?", "Geography"));
			qrep.save(new Question("Photosynthesis", "What is Photosynthesis ?", "Biology"));
			qrep.save(new Question("Object Oriented Languages", "What are some Object Oriented Languages ?", "Software"));
			Question qang = new Question("Alternatives to Angular","Are there any front-end mvc frameworks other than Angular", "OO");
			Answer a1 = new Answer("VueJS");
			Answer a2 = new Answer("ReactJS");
			List<Answer> ans = new ArrayList<Answer>();
			ans.add(a1);
			ans.add(a2);
			qang.setAnswers(ans);
			System.out.println(qang);
			System.out.println(qrep.save(qang));

			QnAUser u1 = new QnAUser("Joe","joe@joe.com","apple");
			QnAUser u2 = new QnAUser("Pierre","pierre@joe.com","orange");


			urep.save(u1);
			urep.save(u2);
		};
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleWebUiApplication.class, args);
	}

}
