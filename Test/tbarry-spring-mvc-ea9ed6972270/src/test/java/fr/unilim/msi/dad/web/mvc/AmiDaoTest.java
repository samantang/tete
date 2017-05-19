/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unilim.msi.dad.web.mvc;
import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author aminus
 */
public class AmiDaoTest {
    
    private ViewFriendshipController viewFSC;
    private UserDao userDao;
    private AmiDao amiDao;
    private UserSession userSession;
    private User admin,user,user2;
    
    
    

    
    @Before
    public void setUp() {
        viewFSC = new ViewFriendshipController();
        userDao = new UserDaoImpl();
        amiDao = new AmiDaoImp();
        userSession = new UserSession();
                
        viewFSC.setUserDao(userDao);
        
        user = new User();
        admin = new User();
        user2 = new User();
        user.setPassword("tbarry");
        user.setUsername("tbarry");
        admin.setUsername("admin");
        admin.setPassword("admin");
        user2.setUsername("aminus");
        user2.setPassword("amine");
        userDao.save(admin);
        userDao.save(user);
        userDao.save(user2);
        
        userSession.setUsername("admin");
        
        viewFSC.setUserSession(userSession);
        userDao.setUserSession(userSession);
        amiDao.setUserSession(userSession);
        
        amiDao.setUserDao(userDao);
        viewFSC.setAmiDao(amiDao);
    }
    
    @Test
    public void testgetAllFriends() {
     amiDao.addFriendship("tbarry");
     ArrayList<Ami> list1 = new ArrayList<Ami>();
     Ami ami1 = new Ami("admin","tbarry","encours");
     list1.add(ami1);
     assertEquals(1,amiDao.getAllFriends().size());
     assertEquals(list1,amiDao.getAllFriends());
    }
    
   @Test
   public void TestAddFriend(){
       
       assertTrue(amiDao.addFriendship("tbarry"));
       assertFalse(amiDao.addFriendship("admin"));
       assertFalse(amiDao.addFriendship("tbarry"));
       assertFalse(amiDao.addFriendship("amine"));
       
       
   }
   
   @Test
   public void TestAcceptation(){
       amiDao.addFriendship("tbarry");
       userSession.setUsername("tbarry");
       amiDao.setUserSession(userSession);
       userDao.setUserSession(userSession);
       amiDao.setUserDao(userDao);
       
       assertTrue(amiDao.acceptFriendShip("admin"));
       assertFalse(amiDao.acceptFriendShip("tbarry"));
       assertFalse(amiDao.acceptFriendShip("amine"));
   }
   
   
   @Test
   public void TestgetEmittedRequests(){
       
       amiDao.addFriendship("tbarry");
       amiDao.addFriendship("aminus");
       assertEquals(2,amiDao.getEmittedRequests("admin").size());
       ArrayList<String> requests = new ArrayList<String>();
       requests.add("tbarry"); requests.add("aminus");
       assertEquals(requests,amiDao.getEmittedRequests("admin"));
       ArrayList<String> requests2 = new ArrayList<String>();
       requests2.add("aminus"); requests2.add("tbarry");
       assert(!requests2.equals(amiDao.getEmittedRequests("admin")));
    
   }
    
    
    
}
