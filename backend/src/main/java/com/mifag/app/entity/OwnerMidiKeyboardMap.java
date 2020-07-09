package com.mifag.app.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity, link table of midi keyboards and owners.
 */
@Entity
@Table(name = "owner_midi_keyboard_map")
public class OwnerMidiKeyboardMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Owner owner;

    @ManyToOne(fetch = FetchType.LAZY)
    private MidiKeyboard midiKeyboard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public MidiKeyboard getMidiKeyboard() {
        return midiKeyboard;
    }

    public void setMidiKeyboard(MidiKeyboard midiKeyboard) {
        this.midiKeyboard = midiKeyboard;
    }
}
