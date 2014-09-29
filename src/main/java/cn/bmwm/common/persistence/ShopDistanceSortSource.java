package cn.bmwm.common.persistence;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.FieldComparator;
import org.apache.lucene.search.FieldComparatorSource;

/**
 * 商铺按距离自定义排序
 * @author zby
 * 2014-9-28 下午2:12:19
 */
public class ShopDistanceSortSource extends FieldComparatorSource {

	private static final long serialVersionUID = -2215511673074087115L;
	
	/**
	 * 纬度
	 */
	private double latitude;
	
	/**
	 * 经度
	 */
	private double longitude;
	
	public ShopDistanceSortSource(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	@Override
	public FieldComparator<?> newComparator(String fieldname, int numHits,
			int sortPos, boolean reversed) throws IOException {
		return new ShopDistanceComparator(numHits);
	}
	
	private class ShopDistanceComparator extends FieldComparator<Object> {
		
		private String[] values;
		
		public ShopDistanceComparator(int numHits) {
			values = new String[numHits];
		}

		@Override
		public int compare(int slot1, int slot2) {
			
			String s1 = values[slot1];
			String s2 = values[slot2];
			
			if(s1 == null || s1.trim().equals("")) {
				return -1;
			}
			
			if(s2 == null || s2.trim().equals("")) {
				return 1;
			}
			
			String[] ss1 = s1.split(",");
			String[] ss2 = s2.split(",");
			
			if(ss1 == null || ss1.length != 2) {
				return -1;
			}
			
			if(ss2 == null || ss2.length != 2) {
				return 1;
			}
			
			
			
			
			
			return 0;
			
		}

		@Override
		public void setBottom(int slot) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int compareBottom(int doc) throws IOException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void copy(int slot, int doc) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setNextReader(IndexReader reader, int docBase)
				throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Object value(int slot) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
}
