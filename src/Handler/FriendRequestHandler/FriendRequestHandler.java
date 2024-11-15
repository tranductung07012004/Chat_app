package Handler.FriendRequestHandler;

import GUI.UserFriendRequest.FriendRequestFrame;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

public class FriendRequestHandler {
    private FriendRequestFrame friendRequestFrame;

    public FriendRequestHandler(FriendRequestFrame friendRequestFrame) {
        this.friendRequestFrame = friendRequestFrame;
    }

    public void handleBack() {
        friendRequestFrame.mainFrame.showChatPanel();
    }

    public void handleSearch(String searchText) {
        List<String> filteredRequests = friendRequestFrame.friendRequests.stream()
                .filter(request -> request.toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
        friendRequestFrame.refreshRequests(filteredRequests);
    }

    public void handleAccept(String request) {
        friendRequestFrame.friendRequests.remove(request);
        friendRequestFrame.refreshRequests(friendRequestFrame.friendRequests);
        JOptionPane.showMessageDialog(null, "Friend request accepted.");
    }

    public void handleDecline(String request) {
        friendRequestFrame.friendRequests.remove(request);
        friendRequestFrame.refreshRequests(friendRequestFrame.friendRequests);
        JOptionPane.showMessageDialog(null, "Friend request declined.");
    }

    // Handle search for friends by name
    public void handleFriendSearch(String searchQuery) {
        // Simulate search results (replace with actual search logic)
        List<String> searchResults = new ArrayList<>();
        if ("John".toLowerCase().contains(searchQuery.toLowerCase())) {
            searchResults.add("John");
        }
        if ("Doe".toLowerCase().contains(searchQuery.toLowerCase())) {
            searchResults.add("Doe");
        }
        if (searchResults.isEmpty()) {
            searchResults.add("Find 'John' for example");
        }

        friendRequestFrame.updateSearchResults(searchResults);
    }

    // Handle sending a friend request
    public void handleSendFriendRequest(String friendName) {
        JOptionPane.showMessageDialog(friendRequestFrame, "Friend request sent to " + friendName + "!");
    }
}
