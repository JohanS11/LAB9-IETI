package eci.ieti;

import eci.ieti.data.CustomerRepository;
import eci.ieti.data.ProductRepository;
import eci.ieti.data.TodoRepository;
import eci.ieti.data.UserRepository;
import eci.ieti.data.model.Customer;
import eci.ieti.data.model.Product;

import eci.ieti.data.model.Todo;
import eci.ieti.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoRepository todoRepository;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        MongoOperations mongoOperation = (MongoOperations) applicationContext.getBean("mongoTemplate");

        customerRepository.deleteAll();
        userRepository.deleteAll();
        todoRepository.deleteAll();

        customerRepository.save(new Customer("Alice", "Smith"));
        customerRepository.save(new Customer("Bob", "Marley"));
        customerRepository.save(new Customer("Jimmy", "Page"));
        customerRepository.save(new Customer("Freddy", "Mercury"));
        customerRepository.save(new Customer("Michael", "Jackson"));

        User user1 = userRepository.save(new User(1L,"Johan Arias","johan@mail.com"));
        User user2 = userRepository.save(new User(2L,"Johan Amador","johanAm@mail.com"));
        User user3 = userRepository.save(new User(3L,"Jaime Arias","jaime@mail.com"));
        User user4 = userRepository.save(new User(4L,"Jeanet Amador","Jeanet@mail.com"));
        User user5 = userRepository.save(new User(5L,"John Alexander","John@mail.com"));
        User user6 = userRepository.save(new User(6L,"Felipe","Felipe@mail.com"));
        User user7 = userRepository.save(new User(7L,"Andrea","Andrea@mail.com"));
        User user8 = userRepository.save(new User(8L,"Mariana","Mariana@mail.com"));
        User user9 = userRepository.save(new User(9L,"Samari","Samari@mail.com"));
        User user10 = userRepository.save(new User(10L,"Camila","Camila@mail.com"));


        todoRepository.save(new Todo("First Todo a@aaaaaaaaaaa22aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",1,new Date(System.currentTimeMillis() - 99999),user1,"Ready"));
        todoRepository.save(new Todo("Second Todo a@aaaaaaaaaaa22aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",2,new Date(System.currentTimeMillis()- 99999),user2,"In Progress"));
        todoRepository.save(new Todo("Third Todo a@aaaaaaaaaaa22aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",12,new Date(System.currentTimeMillis()+ 9999),user3,"Done"));
        todoRepository.save(new Todo("Fourth Todo",23,new Date(System.currentTimeMillis()+9999),user4,"Ready"));
        todoRepository.save(new Todo("Fifth Todo",10,new Date(System.currentTimeMillis()+99999),user5,"Ready"));
        todoRepository.save(new Todo("Sixth Todo",11,new Date(System.currentTimeMillis()- 99999),user6,"In Progress"));
        todoRepository.save(new Todo("Seventh Todo",12,new Date(System.currentTimeMillis()+99999),user7,"Ready"));
        todoRepository.save(new Todo("Eighth Todo",13,new Date(System.currentTimeMillis()+999999),user8,"Done"));
        todoRepository.save(new Todo("Ninth Todo",1,new Date(System.currentTimeMillis()+99999),user1,"Ready"));
        todoRepository.save(new Todo("Tenth Todo",12,new Date(System.currentTimeMillis()- 99999),user2,"Done"));
        todoRepository.save(new Todo("Eleventh Todo",3,new Date(System.currentTimeMillis()+99999),user9,"Ready"));
        todoRepository.save(new Todo("Twelfth Todo",4,new Date(System.currentTimeMillis()- 99999),user9,"Done"));
        todoRepository.save(new Todo("Thirteenth Todo",5,new Date(System.currentTimeMillis()- 99999),user10,"Ready"));
        todoRepository.save(new Todo("Fourteenth Todo",6,new Date(System.currentTimeMillis()- 99999),user1,"In Progress"));
        todoRepository.save(new Todo("Fifteenth Todo",22,new Date(System.currentTimeMillis()+99999),user2,"Ready"));
        todoRepository.save(new Todo("Sixteenth Todo",23,new Date(System.currentTimeMillis()+9999),user3,"Done"));
        todoRepository.save(new Todo("Seventeenth Todo",6,new Date(System.currentTimeMillis()- 99999),user4,"In Progress"));
        todoRepository.save(new Todo("Eighteenth Todo",7,new Date(System.currentTimeMillis()+99999),user5,"Ready"));
        todoRepository.save(new Todo("Nineteenth Todo",10,new Date(System.currentTimeMillis()+9999),user6,"Done"));
        todoRepository.save(new Todo("twentieth Todo",11,new Date(System.currentTimeMillis()+99999),user7,"In Progress"));
        todoRepository.save(new Todo("twenty-first Todo",12,new Date(System.currentTimeMillis()+99999),user8,"Done"));
        todoRepository.save(new Todo("twenty-second Todo",13,new Date(System.currentTimeMillis()+99999),user9,"In Progress"));
        todoRepository.save(new Todo("twenty-third Todo",14,new Date(System.currentTimeMillis()- 99999),user10,"Ready"));
        todoRepository.save(new Todo("twenty-fourth Todo",24,new Date(System.currentTimeMillis()+9999),user1,"In Progress"));
        todoRepository.save(new Todo("twenty-fifth Todo",25,new Date(System.currentTimeMillis()+99999),user2,"In Progress"));


        // QUERY #1 --------
        // Todos where the dueDate has expired
        System.out.println("-----QUERY 1--------");
        Query query = new Query();
        query.addCriteria(Criteria.where("dueDate").lt(new Date()));

        List<Todo> expiredTodos = mongoOperation.find(query,Todo.class);
        System.out.println("Expired Todos: "+expiredTodos.size());
        for(Todo todo : expiredTodos){
            System.out.println(todo.toString());
        }

        // QUERY #2
        // Todos that are assigned to given user and have priority greater equal to 5
        System.out.println("-----Search of todos by Responsible (QUERY2)-------");
        Query query2 = new Query();
        query2.addCriteria(Criteria.where("priority").gte(5).andOperator(Criteria.where("responsible").is(user1)));
        List<Todo> todos2 = mongoOperation.find(query2,Todo.class);
        for(Todo todo : todos2){
            System.out.println(todo.toString());
        }

        // QUERY #3
        // Users that have assigned more than 2 Todos.
        // Can't implement

        System.out.println("--QUERY3 CAN'T BE IMPLEMENTED UNLESS WE CHANGE THE MODEL---");

        // QUERY #4
        //Todos that contains a description with a length greater than 30 characters

        System.out.println("-----QUERY 4------");
        System.out.println();
        Query query3 = new Query();
        query3.addCriteria(Criteria.where("description").regex("[a-z,A-Z,0-9,' ']{30,}"));
        List<Todo> todos3 = mongoOperation.find(query3,Todo.class);
        for(Todo todo : todos3){
            System.out.println(todo.toString());
        }

        
        /* System.out.println("Paginated search of products by criteria:");
        System.out.println("-------------------------------");
        
        productRepository.findByDescriptionContaining("plus", PageRequest.of(0, 2)).stream()
        	.forEach(System.out::println);
        */

    }

}