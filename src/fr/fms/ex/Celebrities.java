package fr.fms.ex;
import java.util.*;


public class Celebrities {

    //Exception pour les données des célébrités qui ne sont pas valides
    public static class InvalidGuestDataException extends Exception {
        public InvalidGuestDataException(String message) {
            super(message);
        }
    }
	
	// This method find guests who are known by every other guests
	// so they are a potential celebrities
	public static Set<Integer> findKnownByEveryone(Map<Integer, String> guestNames , Map<Integer, List<Integer>> knownGuests) throws InvalidGuestDataException{

        //ajouts des exceptions pour liste null ou vide
        if (guestNames == null) {
            throw new IllegalArgumentException("La liste des invités ne peut pas être null");
        }
        if (knownGuests == null) {
            throw new IllegalArgumentException("La liste des connaissances ne peut pas être null!");
        }
        if (guestNames.isEmpty()){
            throw new InvalidGuestDataException("La liste des invités ne peut pas être vide!");
        }

		// We initialize our set to stock potentialCelebrities (known by all)
		Set<Integer> potentialCelebrities = new HashSet<>();

		// We check for each guest 
		for (Integer guestId : guestNames.keySet()) {
            //exception pour l'id du guest (si, il est nul)
            if (guestId == null) {
                throw new InvalidGuestDataException("Un ID d'invité ne peut pas être null!");
            }
			boolean knownByAll = true;
			//de même pour otherGuest
			for (Integer otherGuestId: guestNames.keySet()) {
                if (otherGuestId == null) {
                    throw new InvalidGuestDataException("Un ID d'invité ne peut pas être null");
                }
				
				if (!otherGuestId.equals(guestId)) {
					List<Integer> known = knownGuests.get(otherGuestId);
					
					// If any guest does not know this guest, then they are not a celebrity
					if (known == null || !known.contains(guestId)) {
						knownByAll = false;
						break;
					}
				}
			}
			
			// If a guest is known by everyone, we add him to potential celebrities
			if (knownByAll) {
				potentialCelebrities.add(guestId);
			}
		}
		return potentialCelebrities;
	}
	
	// This method filters the list of potential celebrities where
    // a true celebrity must only know other celebrities
	public static Set<Integer> filteringCelebrities(Set<Integer> candidates, Map<Integer, List<Integer>> knownGuests) {
		
		// We create a copy of candidates
	    Set<Integer> celebrities = new HashSet<>(candidates);
	    
	    // boolean to know if it was changed or not (to know if
	    // we must continue to filter or not)
	    boolean changed;

	 // Repeat until the list of celebrities stabilizes
	    do {
	        changed = false;
	        
	        // We put every candidate that must be deleted on that turn 
	        Set<Integer> setToRemove = new HashSet<>();

	        //For each candidate still in the celebrities list, we search
	        // who they know
	        for (Integer candidateId : celebrities) {
	            List<Integer> knownByCandidate = knownGuests.get(candidateId);
	            
	         // Check if the candidate knows only other celebrities, if not then
	            // he/she can't be a celebrities and we add him to setToRemove and
	            // stop checking for this candidate
	            if (knownByCandidate != null) {
	                for (Integer knownId : knownByCandidate) {
	                    if (!celebrities.contains(knownId)) {
	                        setToRemove.add(candidateId);
	                        break;
	                    }
	                }
	            }
	        }
	        
	        // Remove candidates who know non-celebrities guests and 
	        // we set the boolean changed to true, to specify we must do another
	        // iteration
	        if (!setToRemove.isEmpty()) {
	            celebrities.removeAll(setToRemove);
	            changed = true;
	        }
	       
	        // This loop continue while changes were done (we know it because of 
	        // the boolean)
	    } while (changed);

	    return celebrities;
	}

	public static void main(String[] args) {
		
		// Our guests list with their id and names
        Map<Integer, String> guestNames = new HashMap<>();
        guestNames.put(1, "Albert");
        guestNames.put(2, "Bénédicte");
        guestNames.put(3, "Christophe");
        guestNames.put(4, "Delphine");
        guestNames.put(5, "Edouard");
        guestNames.put(6, "Françoise");
        guestNames.put(7, "Gaston");
        guestNames.put(8, "Héloïse");

        // Id and known guests 
        Map<Integer, List<Integer>> knownGuests = new HashMap<>();
        knownGuests.put(1, Arrays.asList(2, 5, 6));
        knownGuests.put(2, Arrays.asList(3, 5, 6));
        knownGuests.put(3, Arrays.asList(2, 4, 5, 6));
        knownGuests.put(4, Arrays.asList(1, 2, 5, 6, 8));
        knownGuests.put(5, Arrays.asList(6));
        knownGuests.put(6, Arrays.asList(5));
        knownGuests.put(7, Arrays.asList(2, 3, 5, 6, 8));
        knownGuests.put(8, Arrays.asList(2, 4, 5, 6 , 7));
        
        // We find every guest who is known by everyone
        Set<Integer> knownByEveryone = findKnownByEveryone(guestNames, knownGuests);
        
        // Then we filter if they known other celebrities
        Set<Integer> validatedCelebrities = filteringCelebrities(knownByEveryone, knownGuests);
        
        //Final display of all celebrities found or a message to say there is none
        System.out.println("============================");
        System.out.println("Célébrités identifiées :\n");
        if (validatedCelebrities.isEmpty()) {
        	System.out.println("Aucune célébrité trouvé :(");
        } else {
        	for (Integer id : validatedCelebrities) {
                 System.out.println("\t- " + guestNames.get(id));
              }
        }
      
        System.out.println("\n============================");
	}

}
