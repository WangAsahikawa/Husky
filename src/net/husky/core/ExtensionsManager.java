package net.husky.core;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sun.tools.javac.Main;

import net.husky.logic.Module;
import net.husky.logic.Plugin;
import net.husky.logic.Scheduled;
import net.husky.protobuf.Protocol.MessageHandler;

public class ExtensionsManager implements Runnable{
	
	public static int scheduledTimeout=7200;

	private static String filePath=System.getProperty("user.dir")
			+System.getProperty("file.separator")+"extension";
	
	private static String packageName="logic.";
	
	private static Map<String, Long> files=new ConcurrentHashMap<String, Long>();
	
	private static Map<Integer, Module> modules=new ConcurrentHashMap<Integer, Module>();
	
	private static Map<String, Plugin> plugins=new ConcurrentHashMap<String, Plugin>();
	
	private static Map<String, Scheduled> scheduleds=new ConcurrentHashMap<String, Scheduled>();
	
	private static Map<String, Object> entities=new ConcurrentHashMap<String, Object>();
	
	private Thread thread;
	
	private int interval=10000;
	
	public ExtensionsManager()
	{
		thread=new Thread(this);
	}
	
	public void start()
	{
		thread.start();
	}
	
	public void process()
	{
		compileJava(filePath);
		loadClass(filePath);
	}
	
	public void setInterval(int interval) {
		this.interval = interval;
	}
	
	private boolean isFileUpdate(String name,long newTime) {
		if(files.get(name)==null||files.get(name)<newTime)
		{
			return true;
		}
		return false;
	}
	
	private void updateFileTime(String name,long newTime) {
		if(files.get(name)!=null)
		{
			files.remove(name);
		}
		files.put(name, newTime);
	}
	
	private void compile(String path,String name)
	{
		Main.compile(new String []{path+System.getProperty("file.separator")+name});
	}
	
	@SuppressWarnings("resource")
	private void urlLoadClass(String name)
	{
		try 
		{
			URL url = new File(filePath).toURI().toURL();
			URLClassLoader urlCL=new URLClassLoader(new URL[]{url});
			String className=packageName+name;
			Class<?> clazz=urlCL.loadClass(className);
			Object instance=clazz.newInstance();
			String type=(String)clazz.getMethod("type").invoke(instance);
			if(type.equals("Module"))
			{
				int id=(int)clazz.getMethod("register").invoke(instance);
				if(modules.get(id)!=null)
				{
					modules.remove(id);
				}
				modules.put(id, (Module)instance);
				System.out.println("register "+className+" as "+MessageHandler.valueOf(id).name());
			}
			else if(type.equals("Plugin"))
			{
				String pluginName=(String)clazz.getMethod("register").invoke(instance);
				if(plugins.get(pluginName)!=null)
				{
					plugins.remove(pluginName);
				}
				plugins.put(pluginName, (Plugin)instance);
				System.out.println("register "+className+" pluginname: "+pluginName);
			}
			else if(type.equals("Entity"))
			{
				String entityName=(String)clazz.getMethod("register").invoke(instance);
				Object entity=clazz.getMethod("invoke").invoke(instance);
				if(entities.get(entityName)!=null)
				{
					entities.remove(entityName);
				}
				entities.put(entityName, entity);
				System.out.println("register "+className
						+" as a entity with key:"+entityName+" value:"+entity.toString());
			}
			else if(type.equals("Scheduled"))
			{
				if(scheduleds.get(className)!=null)
				{
					scheduleds.remove(className);
				}
				scheduleds.put(className, (Scheduled)instance);
				System.out.println("register "+className+" as a scheduled");
			}
			else
			{
				instance=null;
			}
			urlCL=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void compileJava(String path)
	{
		File dir=new File(path+System.getProperty("file.separator")+"logic");
		File [] fileList=dir.listFiles();
		for(File f:fileList)
		{
			String name=f.getName();
			if(name.lastIndexOf(".java")>0)
			{
				long fileTime=f.lastModified();
				String className=name.substring(0, name.lastIndexOf("."));
				if(isFileUpdate(name, fileTime))
				{
					compile(dir.getPath(), name);
					updateFileTime(name, fileTime);
					System.out.println("compile "+className);
				}
			}
		}
	}
	
	private void loadClass(String path)
	{
		File dir=new File(path+System.getProperty("file.separator")+"logic");
		File [] fileList=dir.listFiles();
		for(File f:fileList)
		{
			String name=f.getName();
			if(name.lastIndexOf(".class")>0)
			{
				long fileTime=f.lastModified();
				String className=name.substring(0, name.lastIndexOf("."));
				if(isFileUpdate(name, fileTime))
				{
					urlLoadClass(className);
					updateFileTime(name, fileTime);
					System.out.println("load "+className);
				}
			}
		}

	}
	
	@Override
	public void run() {
		try 
		{
			while(true)
			{
				process();
				Thread.sleep(interval);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static boolean isModule(int methodId)
	{
		if(modules.get(methodId)!=null)
		{
			return true;
		}
		return false;
	}
	
	public static Module getModule(int methodId)
	{
		return modules.get(methodId);
	}
	
	public static Plugin getPlugin(String pluginName)
	{
		return plugins.get(pluginName);
	}
	
	public static Scheduled getsScheduled(String className)
	{
		return scheduleds.get(className);
	}
	
	public static Object getEntity(String name)
	{
		return entities.get(name);
	}
}
