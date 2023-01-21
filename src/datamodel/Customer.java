package datamodel;

import java.util.*;

/**
 * Class for entity type <i>Customer</i>.
 * <p>
 * Customer is an individual (a person, not a business) who creates and holds (owns) orders in the system.
 * </p>
 *
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 */
public class Customer {
    /**
     * Unique Customer id attribute, {@code id < 0} is invalid, id can only be set once.
     */
    private long id = -1;

    /**
     * Customer's surname attribute, never null.
     */
    private String lastName = "";

    /**
     * None-surname name parts, never null.
     */
    private String firstName = "";

    /**
     * Customer contact information with multiple contacts.
     */
    private final List<String> contacts = new ArrayList<>();

    /**
     * Default constructor.
     */
    public Customer() {
        // TODO implement here
    }

    /**
     * Constructor with single-String name argument.
     *
     * @param name single-String Customer name, e.g. "Eric Meyer".
     * @throws IllegalArgumentException if name argument is null.
     */
    public Customer(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name null.");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("name empty.");
        }
        setName(name);
    }

    /**
     * Id getter.
     *
     * @return customer id, returns {@code null}, if id is unassigned.
     */
    public Long getId() {
        if (this.id == -1) {
            return null;
        } else return id;
    }

    /**
     * Id setter. Id can only be set once with valid id, id is immutable after assignment.
     *
     * @param id value to assign if this.id attribute is still unassigned {@code id < 0} and id argument is valid.
     * @return chainable self-reference.
     * @throws IllegalArgumentException if id argument is invalid ({@code id < 0}).
     */
    public Customer setId(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("invalid id (negative).");
        }
        this.id = this.id == -1 ? id : this.id;
        return this;
    }

    /**
     * LastName getter.
     *
     * @return value of lastName attribute, never null, mapped to "".
     */
    public String getLastName() {
        if (this.lastName == null) {
            return "";
        }
        return this.lastName;
    }

    /**
     * FirstName getter.
     *
     * @return value of firstName attribute, never null, mapped to "".
     */
    public String getFirstName() {
        if (this.firstName == null) {
            return "";
        }
        return this.firstName;
    }

    /**
     * Full name getter
     *
     * @return value of full name, where first and last names are separated by comma. Never null, mapped to "".
     */
    public String getName() {
        return getFirstName() + ", " + getLastName();
    }

    /**
     * Setter that splits a single-String name (for example, "Eric Meyer") into first-
     * ("Eric") and lastName ("Meyer") parts and assigns parts to corresponding attributes.
     *
     * @param first value assigned to firstName attribute, null is ignored.
     * @param last  value assigned to lastName attribute, null is ignored.
     * @return chainable self-reference.
     */
    public Customer setName(String first, String last) {
        if (first == null || last == null) {
            return this;
        }
        this.firstName = first;
        this.lastName = last;
        return this;
    }

    /**
     * Setter that splits a single-String name (e.g. "Eric Meyer") into first- and
     * lastName parts and assigns parts to corresponding attributes.
     *
     * @param name single-String name to split into first- and lastName parts.
     * @return chainable self-reference.
     * @throws IllegalArgumentException if name argument is null.
     */
    public Customer setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException();
        }
        return splitName(name);
    }

    /**
     * Return number of contacts.
     *
     * @return number of contacts.
     */
    public int contactsCount() {
        return this.contacts.size();
    }

    /**
     * Contacts getter (as {@code String[]}).
     *
     * @return contacts (as {@code String[]}).
     */
    public String[] getContacts() {
        return contacts.toArray(new String[0]);
    }

    /**
     * Add new contact for Customer. Only valid contacts (not null, "" or duplicates) are added.
     *
     * @param contact valid contact (not null or "" nor duplicate), invalid contacts are ignored
     * @return chainable self-reference
     * @throws IllegalArgumentException if contact arguments is null or empty "" String.
     */
    public Customer addContact(String contact) {
        if (contact == null) {
            throw new IllegalArgumentException("contact is null.");
        }
        if (contact.length() < 6) {
            throw new IllegalArgumentException("contact less than 6 characters: \"" + contact + "\".");
        }
        if (this.contacts.contains(contact)) {
            return this;
        }

        // ^\"|\"$|\"|,|;|'|t|n , "^\"|\"$"
        String tempContact = contact.replaceAll("^\"|\"$|\"|,|;|'|\t|\n", "").trim();
        if (tempContact.length() < 6) {
            throw new IllegalArgumentException("contact less than 6 characters: \"" + contact + "\".");
        }
        contacts.add(tempContact);
        return this;
    }

    /**
     * Delete the i-th contact with {@code i >= 0} and {@code i < contactsCount()}, otherwise method has no effect.
     *
     * @param i index of contact to delete.
     */
    public void deleteContact(int i) {
        if (i >= 0 && i < contactsCount()) {
            contacts.remove(i);
        }
    }

    /**
     * Delete all contacts.
     */
    public void deleteAllContacts() {
        contacts.clear();
    }

    /**
     * Split single-String name into last- and first name parts.
     *
     * @param name single-String name to split into first- and last name parts.
     * @return chainable self-reference.
     */
    private Customer splitName(String name) {
        // TODO implement here
        if (name.contains(";")) {
            this.firstName = name.substring(name.lastIndexOf(";") + 2);
            this.lastName = name.substring(0, name.lastIndexOf(';'));
            return this;
        }
        if (name.contains(", ")) {
            List<String> names = Arrays.asList(name.split(",[ ]*"));
            this.firstName = names.get(1);
            this.lastName = names.get(0);
//            this.firstName = name.substring(name.lastIndexOf(",") + 2);
//            this.lastName = name.substring(0, name.lastIndexOf(','));
            return this;
        }
        if (name.contains(" ")) {
            this.firstName = name.substring(0, name.lastIndexOf(' '));
            this.lastName = name.substring(name.lastIndexOf(" ") + 1);
            return this;
        }
        if (!name.contains(" ") || !name.contains(";") || !name.contains(", ")) {
            this.lastName = name;
            return this;
        }
        return this;
    }

//    private long throw_() {
//        throw new IllegalArgumentException("ID is already set");
//    }

    /*
     * Was kann der Grund sein, warum die SetterMethoden, z.B. +setId(id: long): Customer nicht wie
     * üblich void als Rückgabewert haben, sondern eine Referenz auf Customer?
     * ANTWORT: Damit man mit der Referenz auf das Customer Objekt weiterarbeiten kann,
     *          in Form von method chaining o.ä.
     *
     * Welcher Wert soll die Methode als Ergebnis liefern?
     * ANTWORT: liefert eine Referenz auf das Customer Objekt zurück
     *
     * Was ist methodchaining?
     * ANTWORT: method chaining beschreibt das aufrufen von mehreren methoden hintereinander,
     *          welche jeweils ein Objekt zurückgeben mit der die nächste Methode weiterarbeiten kann ohne das,
     *          dass Objekt in einer Variable zwischengespeichert werden muss.
     *
     * Was ermöglicht chainable methods?
     * ANTWORT: Schnelleren und effizienter Code, da man keine zwischenvariablen benutzen muss um den Rückgabewert
     *          einer Methode erstmal zwischenzuspeichern bevor man weiterarbeitet.
     *
     */


}