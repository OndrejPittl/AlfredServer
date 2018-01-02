package cz.ondrejpittl.business.services;

import cz.ondrejpittl.mappers.UserRestMapper;
import cz.ondrejpittl.persistence.domain.Post;
import cz.ondrejpittl.persistence.domain.Sex;
import cz.ondrejpittl.persistence.domain.Tag;
import cz.ondrejpittl.persistence.domain.User;
import cz.ondrejpittl.persistence.repository.PostRepository;
import cz.ondrejpittl.persistence.repository.TagRepository;
import cz.ondrejpittl.persistence.repository.UserRepository;
import cz.ondrejpittl.rest.dtos.UserDTO;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.*;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    UserRestMapper userMapper;



    // @TODO: ---- development-only:
    @Transactional
    public boolean init() {
        // 1. delete DB tables
        // 2.:
        User u1 = new User("František", "Plachý", "frantisek.plachy@gmail.com", Sex.MALE, "", "frantisek-plachy", "fc866007c6f32559d0a73bf82c718670");
        User u2 = new User("Eliška", "Nesmělá", "eliska.nesmela@seznam.cz", Sex.FEMALE, "http://via.placeholder.com/1000x1000", "eliska-nesmela", "a15fc1614ad4d674cf44f54e69f20db0");
        User u3 = new User("Dominik", "Vzpurný", "dominik.vzpurny@yahoo.com", Sex.MALE, "http://via.placeholder.com/1000x1000", "dominik-vzpurny", "4bd2b007716888ed6bf6c2399a6d7305");

        Tag t1 = new Tag("trip");
        Tag t2 = new Tag("travelling");
        Tag t3 = new Tag("prague");
        Tag t4 = new Tag("shortestway");
        Tag t5 = new Tag("coffee");
        Tag t6 = new Tag("aeropress");
        Tag t7 = new Tag("filteredcoffee");
        Tag t8 = new Tag("coffeepreparation");
        Tag t9 = new Tag("cat");
        Tag t10 = new Tag("pet");
        Tag t11 = new Tag("treatment");
        Tag t12 = new Tag("wonders");
        Tag t13 = new Tag("sevenwonders");
        Tag t14 = new Tag("hario");
        Tag t15 = new Tag("v60");
        Tag t16 = new Tag("intel");
        Tag t17 = new Tag("computer");
        Tag t18 = new Tag("computerscience");
        Tag t19 = new Tag("france");
        Tag t20 = new Tag("paris");
        Tag t21 = new Tag("eiffeltower");

        Post p1 = new Post("The shortest way Václavské náměstí – Staroměstské náměstí", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", null, new Date(), u1, new HashSet<Tag>(){{ add(t1); add(t2); add(t3); add(t4); }});
        Post p2 = new Post("AeroPress Coffee preparation", "Place one round AeroPress filter into the bottom of a filter cap and screw on the brew chamber. Place brewing chamber on the top of the mug. Add 17 g of freshly ground coffee. Pour with water at a temperature between 93 and 94 °C. Then give it a good stir with the included stirrer so that the grounds settle back down. Add the remaining water so that the brew is at the top of the number 2. Let the water steep for about 10 more seconds. After a total brew time between 45 and 90 seconds, place the plunger into the chamber and apply a steady amount of force to move the plunger downward. Remove the brewing unit from your mug. Enjoy.", null, new Date(), u2, new HashSet<Tag>(){{ add(t5); add(t6); add(t7); add(t8); }});
        Post p3 = new Post("How to treat a cat.", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", null, new Date(), u3, new HashSet<Tag>(){{ add(t9); add(t10); add(t11); }});
        Post p4 = new Post("Wonders of the World", "Great Pyramid of Giza, Temple of Artemis at Ephesus, Hanging Gardens of Babylon, Lighthouse of Alexandria, Statue of Zeus at Olympia, Mausoleum at Halicarnassus, Colossus of Rhodes.", null, new Date(), u1, new HashSet<Tag>(){{ add(t12); add(t13); }});
        Post p5 = new Post("Hario v60 brew", "Heat your water to 205 degrees by bringing it to a boil and letting it sit for 30 seconds. Fold the filter along the crimped edge and place it inside in the dripper. Set the dripper on top of your mug. Pour hot water around the inside of the filter for about 5 seconds. Rinsing the filter helps seal it inside the dripper and also gets rid of any papery taste. Take the dripper off of your mug and discard the rinse water. If using pre-ground coffee skip to Step 5. Weigh out your whole bean coffee and grind it on a medium-fine setting. See our guide on how to grind your coffee for reference. Pour your ground coffee into the filter. Gently shake the dripper back and forth to settle the grounds, then set it back on top of your mug. Make a half-inch indent in the middle of the coffee bed with your finger. Time: 0:00-0:45. Start your timer and slowly pour just enough water over the grounds to wet them evenly (40 grams). This step is called the bloom. Hot water forces the coffee to release trapped gases, leading to expansion of the coffee bed, bubbling at the surface, and wonderful aromas for you to enjoy. Let sit until your timer reads 45 seconds. Time: 0:45-1:30. Begin pouring continuously in small circles - about the size of a quarter - around the center of the dripper. Try to keep the water level steady below the rim of the dripper, and avoid pouring around the edges of the filter. Stop pouring once your timer reads 1:30 or your scale reads 300 grams. Time: 1:30-2:00. Allow all the water to drain through the filter. If your final time was longer than 2:15, your grind was probably too fine. If your final time was shorter than 1:45, your grind was probably too coarse. Make a small adjustment to the grind next time you brew - practice makes perfect! Remove the filter from the dripper and discard the grounds.", null, new Date(), u2, new HashSet<Tag>(){{ add(t5); add(t7); add(t8); add(t14); add(t15); }});
        Post p6 = new Post("Intel foundation.", "Intel was founded on July 18, 1968.", null, new Date(), u3, new HashSet<Tag>(){{ add(t16); add(t17); add(t18); }});
        Post p7 = new Post("How to Grind Coffee", "Your coffee grind has a big impact on the quality of your brew. What's the right grind size? It depends on how you brew your coffee! And since there are a lot of different ways to brew a delicious cup, we decided to make you this handy guide so you could explore them all.", null, new Date(), u2, new HashSet<Tag>(){{ add(t5); add(t8); }});
        Post p8 = new Post("How to Store Coffee", "Your beans’ greatest enemies are air, moisture, heat, and light.<br>To preserve your beans’ fresh roasted flavor as long as possible, store them in an opaque, air-tight container at room temperature. Coffee beans can be beautiful, but avoid clear canisters which will allow light to compromise the taste of your coffee.<br>Keep your beans in a dark and cool location. A cabinet near the oven is often too warm, and so is a spot on the kitchen counter that gets strong afternoon sun.<br>Coffee's retail packaging is generally not ideal for long-term storage. If possible, invest in storage canisters with an airtight seal.", null, new Date(), u2, new HashSet<Tag>(){{ add(t5); }});
        Post p9 = new Post("The Eiffel Tower", "The Eiffel Tower is a wrought iron lattice tower on the Champ de Mars in Paris, France. It is named after the engineer Gustave Eiffel, whose company designed and built the tower.", "https://upload.wikimedia.org/wikipedia/commons/thumb/8/85/Tour_Eiffel_Wikimedia_Commons_%28cropped%29.jpg/480px-Tour_Eiffel_Wikimedia_Commons_%28cropped%29.jpg", new Date(), u1, new HashSet<Tag>(){{ add(t1); add(t2); add(t19); add(t20); add(t21); }});

        u1.setPosts(new LinkedList<Post>(){{ add(p1); add(p4); add(p9); }});
        u2.setPosts(new LinkedList<Post>(){{ add(p2); add(p5); add(p7); add(p8); }});
        u3.setPosts(new LinkedList<Post>(){{ add(p3); add(p6); }});

        userRepository.save(u1);
        userRepository.save(u2);
        userRepository.save(u3);

        return true;
    }

    // -----------------------------

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository.findBy(id);
    }

    public User getUser(String slug) {
        return userRepository.findFirst1BySlugLike(slug);
    }


    @Transactional
    public User createUser(UserDTO user) {
        User u = userMapper.fromDTO(user);

        Tag t = new Tag("druhyTag");

        u.setPosts(new LinkedList<Post>(){{
            add(new Post("post1_title", "post1_body", "post1_img-path", new Date(), u, new HashSet<Tag>(){{
                add(new Tag("prvniTag"));
                add(t);
            }}));
            add(new Post("post2_title", "post2_body", "post2_img-path", new Date(), u, new HashSet<Tag>(){{
                add(t);
                add(new Tag("tretiTag"));
            }}));
        }});

        return userRepository.save(u);
    }

    @Transactional
    public User removeUser(Long id) {
        return this.userRepository.removeById(id);
    }
}
