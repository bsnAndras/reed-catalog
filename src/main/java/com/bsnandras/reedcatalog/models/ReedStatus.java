package com.bsnandras.reedcatalog.models;

/**
 * The condition of a Reed.
 * - NEW: newly acquired
 * - ADJUSTED: needed some more work, so should be repriced
 * - USED: selected for myself
 * - SOLD: the reed got sold.
 * - DESTROYED: Damaged reed, that is no longer playable, haven't really used or sold
 */
public enum ReedStatus {
    NEW,
    ADJUSTED,
    USED,
    SOLD,
    DESTROYED
}
