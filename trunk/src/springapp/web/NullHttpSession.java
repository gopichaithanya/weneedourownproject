package springapp.web;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 * This is stub class for HttpSession class. This is only for unit testing.
 * This stub class can store attributes.
 * @see HttpSession
 * @see NullHttpServletRequest
 */
@SuppressWarnings("unchecked")
public class NullHttpSession implements HttpSession {

   private HashMap<Object, Object> attr = new HashMap<Object, Object>();

   public void setAttribute(String arg0, Object arg1) {
      attr.put(arg0, arg1);
   }

   public Object getAttribute(String arg0) {
      return attr.get(arg0);
   }

   public Enumeration getAttributeNames() {
      return null;
   }

   public long getCreationTime() {
      // TODO Auto-generated method stub
      return 0;
   }

   public String getId() {
      // TODO Auto-generated method stub
      return null;
   }

   public long getLastAccessedTime() {
      // TODO Auto-generated method stub
      return 0;
   }

   public int getMaxInactiveInterval() {
      // TODO Auto-generated method stub
      return 0;
   }

   public ServletContext getServletContext() {
      // TODO Auto-generated method stub
      return null;
   }

   public HttpSessionContext getSessionContext() {
      // TODO Auto-generated method stub
      return null;
   }

   public Object getValue(String arg0) {
      // TODO Auto-generated method stub
      return null;
   }

   public String[] getValueNames() {
      // TODO Auto-generated method stub
      return null;
   }

   public void invalidate() {
      // TODO Auto-generated method stub

   }

   public boolean isNew() {
      // TODO Auto-generated method stub
      return false;
   }

   public void putValue(String arg0, Object arg1) {
      // TODO Auto-generated method stub

   }

   public void removeAttribute(String arg0) {
      // TODO Auto-generated method stub

   }

   public void removeValue(String arg0) {
      // TODO Auto-generated method stub

   }

   public void setMaxInactiveInterval(int arg0) {
      // TODO Auto-generated method stub

   }

}
