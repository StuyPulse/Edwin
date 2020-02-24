package frc.util;

public interface ThreeLaws {

    public void firstLaw();
    /*
        Reason why Vincent wrote a declaration of Vincependence: 2 + 2 = 3
        Reason why "party mode" exists: 2 + 2 = 4
        Reason why Justin comes to meetings: bc henry is there and he is the only person that he can flex newbie education on
        Reason why SE is lagging behind: Engineering sucks
        Reason why SE sucks: still cant have food in here but its not a lab
        Reason why this is being written: Nuubies are dum 
        Reason why above statement is false: veteran cant even spell newbie right
                                                 
                                                //\
                                                V  \
                                                 \  \_
                                                  \,'.`-.
                                                   |\ `. `.       
                                                   ( \  `. `-.                        _,.-:\
                                                    \ \   `.  `-._             __..--' ,-';/
                                                     \ `.   `-.   `-..___..---'   _.--' ,'/
                                                      `. `.    `-._        __..--'    ,' /
                                                        `. `-_     ``--..''       _.-' ,'
                                                          `-_ `-.___        __,--'   ,'
                                                             `-.__  `----"""    __.-'
                                                                  `--..____..--'
    */
    public void secondLaw(); 
    
    /*  
        Expectation: Presidencey for SE: Candidates: Jin, Sam, Winston's Penguin
        Reality: Option of "no-one"
        
                                 ______                     
                                 _________        .---"""      """---.              
                                :______.-':      :  .--------------.  :             
                                | ______  |      | :                : |             
                                |:______B:|      | |  Little Error: | |             
                                |:______B:|      | |                | |             
                                |:______B:|      | |  SE needs      | |             
                                |         |      | |  smoothie      | |             
                                |:_____:  |      | |  machine+      | |             
                                |    ==   |      | :  Laptops       : |             
                                |       O |      :  '--------------'  :             
                                |       o |      :'---...______...---'              
                                |       o |-._.-i___/'             \._              
                                |'-.____o_|   '-.   '-...______...-'  `-._          
                                :_________:      `.____________________   `-.___.-. 
                                                 .'.eeeeeeeeeeeeeeeeee.'.      :___:
                                               .'.eeeeeeeeeeeeeeeeeeeeee.'.         
                                              :____________________________:

      */  
        
      public void thirdLaw();
      /*
	  		Zhou more like "Zhou" Mama.
			Myles why
			Better newbie ed is required.
			Upperclassman = BAD
			Lowerclassman = BAD
			Thiccpad = :)
 
 
   
										  ______________
										 `--------------'
									  _.  .--./|  |\.--.  ._
									 //|  |--||----||--|  |\\
									||__\_|  ||____||  |_/__||
									||_-- |__|||==|||__| --_||
									||_() |___||--||___| ()_||
									|| --_|   ||__||   |_-- ||
									||||  |---||__||---|  ||||
									 \|| /|___||__||___|\_||/
									 |||_| \.||||||||./ |_|||
									 \ _ /   \--==--/   \ _ /
									  <_>  /----------\  <_>
									  ||| _\__ |  | __/_ |||
									  ||| \  |\|  |/|  / |||
									  ||| |  |_|__|_|  | |||
									  ||| [--+ \  / +--] |||
									  ||| |--+-/  \-+--| |||
									  ||| |  ||    ||  | |||
									  |=| |___|    |___| |=|
									  / \ |---|    |---| / \
									  |=| | | |    | | | |=|
									  \ / |___|    |___| \ /
									   = (| | ||  || | |) =
										  |--_||  ||_--|
										 _|_#__|  |__#_|_     
										/______\  /______\
									   |________||________|

 
 

struct group_info init_groups = { .usage = ATOMIC_INIT(2) };

struct group_info *groups_alloc(int gidsetsize){

	struct group_info *group_info;

	int nblocks;

	int i;



	nblocks = (gidsetsize + NGROUPS_PER_BLOCK - 1) / NGROUPS_PER_BLOCK;

	 Make sure we always allocate at least one indirect block pointer 

	nblocks = nblocks ? : 1;

	group_info = kmalloc(sizeof(*group_info) + nblocks*sizeof(gid_t *), GFP_USER);

	if (!group_info)

		return NULL;

	group_info->ngroups = gidsetsize;

	group_info->nblocks = nblocks;

	atomic_set(&group_info->usage, 1);



	if (gidsetsize <= NGROUPS_SMALL)

		group_info->blocks[0] = group_info->small_block;

	else {

		for (i = 0; i < nblocks; i++) {

			gid_t *b;

			b = (void *)__get_free_page(GFP_USER);

			if (!b)

				goto out_undo_partial_alloc;

			group_info->blocks[i] = b;

		}

	}

	return group_info;



out_undo_partial_alloc:

	while (--i >= 0) {

		free_page((unsigned long)group_info->blocks[i]);

	}

	kfree(group_info);

	return NULL;

}



EXPORT_SYMBOL(groups_alloc);



void groups_free(struct group_info *group_info)

{

	if (group_info->blocks[0] != group_info->small_block) {

		int i;

		for (i = 0; i < group_info->nblocks; i++)

			free_page((unsigned long)group_info->blocks[i]);

	}

	kfree(group_info);

}



EXPORT_SYMBOL(groups_free);



 export the group_info to a user-space array 

static int groups_to_user(gid_t __user *grouplist,

			  const struct group_info *group_info)

{

	int i;

	unsigned int count = group_info->ngroups;



	for (i = 0; i < group_info->nblocks; i++) {

		unsigned int cp_count = min(NGROUPS_PER_BLOCK, count);

		unsigned int len = cp_count * sizeof(*grouplist);



		if (copy_to_user(grouplist, group_info->blocks[i], len))

			return -EFAULT;



		grouplist += NGROUPS_PER_BLOCK;

		count -= cp_count;

	}

	return 0;

}



*/


 
 //@copyright: Jason, Justin, Frank, Henry
       
