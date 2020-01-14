package com.oppailand.discordrealms.objects;

public class PlayerObject extends MobObject {
	public String user_id;
	public ItemObject[] bank;
	public int gems;
	public int curr_exp;
	public String prefix;

	public PlayerObject() {
		object_type = Type.PLAYER;
	}

	public PlayerObject(MobObject o) {
		super(o);
		object_type = Type.PLAYER;
	}

	public PlayerObject(PlayerObject o) {
		super(o);
		object_type = Type.PLAYER;
		user_id = o.user_id;
		bank = o.bank;
		gems = o.gems;
		curr_exp = o.curr_exp;
		prefix = o.prefix;
	}
}
