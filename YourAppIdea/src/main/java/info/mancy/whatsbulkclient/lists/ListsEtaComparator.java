package info.mancy.whatsbulkclient.lists;

import java.util.Comparator;

public class ListsEtaComparator implements Comparator<Lists> {

	public int compare(Lists f1, Lists f2) {
		return 0;//(int) f1.getEta().getTime() - (int) f2.getEta().getTime();
	}
}
