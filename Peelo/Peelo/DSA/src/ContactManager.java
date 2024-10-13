import javax.swing.*;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import java.awt.event.*;
import java.util.LinkedList;

public class ContactManager {
    private JFrame frame;
    private JTextField nameField, phoneField, emailField, searchField;
    private LinkedList<Contact> contactList;
    private JTable contactTable, findTable;
    private DefaultListModel<String> contactListModel;

    public ContactManager() {
        contactList = new LinkedList<>();
        frame = new JFrame("Contact Manager");
        frame.setLayout(null);  // Set null layout

        // Avatar
        JLabel avatarLabel = new JLabel(new ImageIcon("avatar.png"));
        avatarLabel.setBounds(20, 20, 80, 80);
        frame.add(avatarLabel);

        // Side buttons
        JButton newContactButton = new JButton("Add");
        newContactButton.setBounds(60, 170, 120, 30);
        frame.add(newContactButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(200, 170, 120, 30);
        frame.add(deleteButton);

        JButton editButton = new JButton("Update");
        editButton.setBounds(350, 170, 120, 30);
        frame.add(editButton);


        // Contact fields
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 40, 100, 20);
        frame.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(60, 40, 200, 30);
        frame.add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(10, 80, 100, 20);
        frame.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(60, 80, 200, 30);
        frame.add(emailField);

        JLabel phoneLabel = new JLabel("Cell #:");
        phoneLabel.setBounds(10, 120, 100, 20);
        frame.add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(60, 120, 200, 30);
        frame.add(phoneField);

        // All contacts
        JLabel allContactsLabel = new JLabel("All Contacts");
        allContactsLabel.setBounds(60, 220, 100, 20);
        frame.add(allContactsLabel);

        contactListModel = new DefaultListModel<>();
        JList<String> contactListDisplay = new JList<>(contactListModel);
        JScrollPane scrollPane = new JScrollPane(contactListDisplay);
        scrollPane.setBounds(60, 250, 570, 120);
        frame.add(scrollPane);

        JButton refreshButton1 = new JButton("All");
        refreshButton1.setBounds(500, 170, 120, 30);
        frame.add(refreshButton1);


        searchField = new JTextField();
        searchField.setBounds(60, 390, 200, 30);
        frame.add(searchField);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(280, 390, 80, 30);
        frame.add(searchButton);

        // Results of the search
        JList<String> findList = new JList<>();
        JScrollPane findScrollPane = new JScrollPane(findList);
        findScrollPane.setBounds(60, 430, 570, 100);
        frame.add(findScrollPane);


        // Button listeners

        // Add new contact
        newContactButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String phone = phoneField.getText();
                String email = emailField.getText();
        
                if (!name.isEmpty() && !phone.isEmpty() && !email.isEmpty()) {
                    contactList.add(new Contact(name, phone, email));
                    JOptionPane.showMessageDialog(frame, "Contact added successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "All fields are required.");
                }
            }
        });

        // Delete contact
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = contactListDisplay.getSelectedIndex();
                if (selectedIndex != -1) {
                    contactList.remove(selectedIndex);
                    updateContactListDisplay();
                    JOptionPane.showMessageDialog(frame, "Contact deleted.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a contact to delete.");
                }
            }
        });

        // Edit contact
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = contactListDisplay.getSelectedIndex();
                if (selectedIndex != -1) {
                    String newName = JOptionPane.showInputDialog("Enter new name:");
                    String newEmail = JOptionPane.showInputDialog("Enter new email:");
                    String newPhone = JOptionPane.showInputDialog("Enter new phone:");

                    Contact contact = contactList.get(selectedIndex);
                    contact.setName(newName);
                    contact.setEmail(newEmail);
                    contact.setPhone(newPhone);

                    updateContactListDisplay();
                    JOptionPane.showMessageDialog(frame, "Contact updated.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a contact to edit.");
                }
            }
        });

        // Refresh contacts
        refreshButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateContactListDisplay();
            }
        });

// Search for a contact
searchButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        String searchQuery = searchField.getText().toLowerCase();
        DefaultListModel<String> searchResults = new DefaultListModel<>();
        boolean found = false; // Flag to track if any contact is found

        for (Contact contact : contactList) {
            if (contact.getName().toLowerCase().contains(searchQuery)) {
                searchResults.addElement(contact.toString());
                found = true; // Set to true if a matching contact is found
            }
        }

        if (!found) {
            // No contact found, show a message dialog
            JOptionPane.showMessageDialog(null, "No contact found with the name: " + searchQuery, "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }

        findList.setModel(searchResults); // Update the list with search results (if any)
    }
});



        // Frame settings
        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Update contact display
    private void updateContactListDisplay() {
        contactListModel.clear();
        for (Contact contact : contactList) {
            contactListModel.addElement(contact.toString());
        }
    }

    public static void main(String[] args) {
        //FlatMacDarkLaf.setup();
        new ContactManager();
    }
}

// Contact class to store contact details
class Contact {
    private String name, phone, email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }

    public String toString() {
        return "Name: " + name + ", Phone: " + phone + ", Email: " + email;
    }
}
