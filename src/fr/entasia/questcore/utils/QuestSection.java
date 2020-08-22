package fr.entasia.questcore.utils;

import org.bukkit.configuration.ConfigurationSection;

public class QuestSection {

	public ConfigurationSection sec;

	public QuestSection() {
	}
	public QuestSection(ConfigurationSection sec) {
		this.sec = sec;
	}

	public int getProgress(){
		return sec.getInt("progress");
	}

	public void setProgress(int p){
		sec.set("progress", p);
	}

}
