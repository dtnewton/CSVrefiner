import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class CSVrefiner
{
	public static final int NUM_COLS = 12;//this may change

	public static void main(String[] args)
	{
		ArrayList<String> genres = CSVrefiner.buildGenres();
<<<<<<< HEAD
		CSVrefiner.buildCSV(genres);	          	      
	}	
	
	private static ArrayList<String> buildGenres() 
=======
		Map<String, Integer> gMap = CSVrefiner.buildGenreMap(genres);
		System.out.print(gMap.toString());
		//TODO - build cast/crew list?
		CSVrefiner.buildCSV(gMap);
	}

	private static ArrayList<String> buildGenres()
>>>>>>> 5ee2721058d1dccc3448ad31f57610055adb9bfc
	{
		ArrayList<String> genres = new ArrayList<String>();

		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("genre.txt"), "UTF-8")))
		{
			String ln;
			while((ln = br.readLine()) != null)
			{
				if(ln.startsWith("\uFEFF"))
				{
					ln = ln.substring(1);
				}
				genres.add(ln);
			}
			Collections.sort(genres);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return genres;
	}

<<<<<<< HEAD
	private static void buildCSV(ArrayList<String> genres) 
=======
	private static Map<String, Integer> buildGenreMap(ArrayList<String> genres)
	{

		int numVal = 0;
		Map<String, Integer> gmap = new HashMap<String, Integer>();
		for(String s : genres)
		{
			gmap.put(s,numVal);
			numVal++;
		}

		return gmap;
	}

	private static void buildCSV(Map<String, Integer> gMap)
>>>>>>> 5ee2721058d1dccc3448ad31f57610055adb9bfc
	{
		ArrayList<String> headers = new ArrayList<String>();
		ArrayList<String> vals = new ArrayList<String>();
		String line = null;

		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("movie_profiles_small.csv"), "UTF-8")))
		{
			File file = null;

			try
			{
				file = new File("movie_profiles_small_updated.csv");
				if(!file.exists())
				{
					file.createNewFile();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
<<<<<<< HEAD
				
			BufferedWriter writer = new BufferedWriter(new FileWriter(file)); 
			
			/*first line - column labels***********************************/
=======

			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			/*first line(column labels)*/
>>>>>>> 5ee2721058d1dccc3448ad31f57610055adb9bfc
			line = reader.readLine();
			if(line.startsWith("\uFEFF"))
			{
				line = line.substring(1);
			}

			String[] splitStr = line.trim().split(",");

			for(int i = 0; i < splitStr.length; i++)
			{
<<<<<<< HEAD
			
				if(splitStr[i].equals("genres"))
				{
					for(int j = 0; j < genres.size(); j++)
					{
						headers.add(genres.get(j));
					}
=======

				vals.add(splitStr[i]);
			}

			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < vals.size(); i++)
			{
				if(vals.get(i).equals("genres"))
				{
						sb.append("genre_val");
						sb.append(",");
>>>>>>> 5ee2721058d1dccc3448ad31f57610055adb9bfc
				}
				else
				{
					headers.add(splitStr[i]);
				}
<<<<<<< HEAD
			}
			
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < headers.size(); i++)
			{

				sb.append(headers.get(i));
				if(i < headers.size() - 1) sb.append(",");
			
=======

>>>>>>> 5ee2721058d1dccc3448ad31f57610055adb9bfc
			}

			line = sb.toString();
			writer.write(line);
			writer.newLine();
<<<<<<< HEAD
			
			
			String[] fullLine, gList;
			ArrayList<String> gs = new ArrayList<String>();
			
=======
			vals.clear();
			String[] fullLine;

>>>>>>> 5ee2721058d1dccc3448ad31f57610055adb9bfc
			//*********************************************************************
			while((line = reader.readLine())!=null)
			{
				vals.clear();
				gs.clear();
				
				/* COLLECT VALS */
				fullLine = line.trim().split(",");
				for(int i = 0; i < fullLine.length; i++)
				{
					if(i == 1)//maybe refine this check?
					{
						gList = fullLine[i].split("\\|");
						
						for(int k = 0; k < gList.length; k++)
						{
							gs.add(gList[k]);
						}
						
						for(int j = 0; j < genres.size(); j++)
						{
<<<<<<< HEAD
							if(gs.contains(genres.get(j)))
=======
							writer.write(vals.get(i));
						}
					}
					else
					{
						int numericVal = 0;
						if(vals.get(i).equals(""))
						{
							numericVal = 0;
							writer.write(Integer.toString(numericVal));
							writer.write(",");
						}
						else if(vals.get(i).contains("|"))
						{
							numericVal = 0;
							String[] temp = vals.get(i).split("\\|");
							//System.out.println(vals.get(i));

							ArrayList<String> innerList = new ArrayList<String>();
							for(int j = 0; j < temp.length; j++)
>>>>>>> 5ee2721058d1dccc3448ad31f57610055adb9bfc
							{
								vals.add("1");
							}
							else
							{
								vals.add("0");
							}
<<<<<<< HEAD
=======

							writer.write(Integer.toString(numericVal));
							writer.write(",");

						}
						else
						{
							numericVal = 0;
							numericVal = gMap.get(vals.get(i));
							writer.write(Integer.toString(numericVal));
							writer.write(",");
>>>>>>> 5ee2721058d1dccc3448ad31f57610055adb9bfc
						}
					}
					else
					{
						vals.add(fullLine[i]);
					}
					
				}
					
				/* WRITE */
				for(int i = 0; i < vals.size();i++)
				{
					
					if(!(i == (vals.size() - 1)))
					{
						writer.write(vals.get(i));
						writer.write(",");
					}
					else
					{
						writer.write(vals.get(i));
					}
			
				}

				writer.newLine();
			}
			//*********************************************************************
			writer.close();
			reader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
<<<<<<< HEAD
=======
		//System.out.println(line);
>>>>>>> 5ee2721058d1dccc3448ad31f57610055adb9bfc
	}
}
