package key_process;

import java.util.HashMap;
import java.util.ArrayList;


/**
 * This source have to use when you test MYKeySoftKeyboard only
 */
public class  KeyMap {
	static HashMap<String, Integer> keyMap = new HashMap<String, Integer>();
	static ArrayList<String> stringCodeList = new ArrayList<String>();
	static ArrayList<String> stringLabelList = new ArrayList<String>();
	public KeyMap() {
		keyMap.put("3002",4005);
		keyMap.put("3001",4002);
		keyMap.put("3500-3502",4101);
		keyMap.put("3000",4000);
		keyMap.put("3506-3502",4103);
		keyMap.put("3506-3508",4103);
		keyMap.put("3501-3508",4105);
		keyMap.put("3507-3502",4107);
		keyMap.put("3500-3508",4101);
		keyMap.put("3501-3502",4105);
		keyMap.put("3511",4118);
		keyMap.put("3507-3508",4107);
		keyMap.put("3510",4117);
		keyMap.put("3004-3004",4016);
		keyMap.put("3013",4013);
		keyMap.put("3012",4011);
		keyMap.put("3011",4010);
		keyMap.put("3010",4008);
		keyMap.put("3006-3006",4014);
		keyMap.put("3009",4005);
		keyMap.put("3008",4004);
		keyMap.put("3007",4001);
		keyMap.put("3003-3003",4017);
		keyMap.put("3006",4012);
		keyMap.put("3005",4011);
		keyMap.put("3009-3009",4006);
		keyMap.put("3004",4009);
		keyMap.put("3003",4007);
		keyMap.put("3012-3012",4018);
		keyMap.put("3503-3502",4111);
		keyMap.put("3504-3508",4116);
		keyMap.put("3000-3000",4015);
		keyMap.put("3503-3500",4109);
		keyMap.put("3511-3502",4119);
		keyMap.put("3504-3502",4116);
		keyMap.put("3504-3501",4114);
		keyMap.put("3511-3508",4119);
		keyMap.put("3503-3508",4111);
		keyMap.put("3001-3001",4003);
		keyMap.put("3002-3002",4006);
		keyMap.put("3505-3502",4119);
		keyMap.put("3005-3005",4018);
		keyMap.put("3503-3500-3508",4110);
		keyMap.put("3505",4118);
		keyMap.put("3504",4113);
		keyMap.put("3504-3501-3502",4115);
		keyMap.put("3503",4108);
		keyMap.put("3502",4120);
		keyMap.put("3501",4104);
		keyMap.put("3500",4100);
		keyMap.put("3503-3500-3502",4110);
		keyMap.put("3504-3501-3508",4115);
		keyMap.put("3509",4112);
		keyMap.put("3508",4120);
		keyMap.put("3507",4106);
		keyMap.put("3505-3508",4119);
		keyMap.put("3506",4102);
		stringCodeList.add("4400");		stringLabelList.add("Hansung");
		stringCodeList.add("4401");		stringLabelList.add("MyKey");
		stringCodeList.add("4402");		stringLabelList.add("★");
		stringCodeList.add("4403");		stringLabelList.add("♥");

	}
	public HashMap<String, Integer> getKeyMap() {
		return keyMap;
	}
	public ArrayList<String> getStringCodeList() {
		return stringCodeList;
	}
	public ArrayList<String> getStringLabelList() {
		return stringLabelList;
	}
}