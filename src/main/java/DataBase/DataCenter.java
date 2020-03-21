package DataBase;

import Bean.Event;
import Bean.Merchant;
import Bean.User;
import ServerLogic.getDataImpl;

import java.util.ArrayList;

/*
 此类存储APP运行时数据，主要是从数据库获取的内容，避免频繁在各个Activity间传递对象
 */
public class DataCenter {
    public static Event selectedEvent = null;
    public static Merchant selectedMerchant = null;
    public static int selectedMerchantPosition = 0;
    public static User loginUser = null;
    private static int expectedEventListSize = 10;
    private static int expectedMerchantListSize = 5;
    public static int showEventListSize = 6;
    public static ArrayList<Event> currentMainEventList = new getDataImpl().getEventList(DataCenter.expectedEventListSize);
    public static ArrayList<Merchant> merchantList = new getDataImpl().getMerchantList(DataCenter.expectedMerchantListSize);
}
