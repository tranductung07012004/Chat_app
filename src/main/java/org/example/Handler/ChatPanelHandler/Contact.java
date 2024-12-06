package org.example.Handler.ChatPanelHandler;

public class Contact {

        private String name;
        private boolean online;
        public boolean isGroup;
        public int Id;

        public Contact(String name, boolean online, boolean isGroup, int Id) {
            this.name = name;
            this.online = online;
            this.isGroup = isGroup; // Initialize the isGroup flag
            this.Id=Id;
        }

        public String getName() {
            return name;
        }

        public boolean isOnline() {
            return online;
        }
        public int getId() {
        return Id;
    }

        public boolean isGroup(){return isGroup;}


}
