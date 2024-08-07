/*----------------------------------------------------------------------------
 * Description:   Methods for bridging to an external entity.
 *
 * External Entity:  Architecture (ARCH)
 * 
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#include "lichess_bot_sys_types.h"
#include "ARCH_bridge.h"
#include "ChessLib_bridge.h"
#include "LOG_bridge.h"
#include "PROP_bridge.h"
#include "TIM_bridge.h"
#include "STRING_bridge.h"
#include <stdlib.h>
extern bool Escher_run_flag; /* Turn this false to stop the event queues.  */

/*
 * Bridge:  shutdown
 */
void
ARCH_shutdown()
{
  Escher_run_flag = false; /* default automatic action for this bridge */
  return;
}


/*
 * Bridge:  openURL
 */
void
ARCH_openURL( c_t p_url[ESCHER_SYS_MAX_STRING_LEN] )
{
  char c[ESCHER_SYS_MAX_STRING_LEN];
  char * cmd = c;
  cmd = Escher_stradd( "open ", p_url );
  system(cmd);
}

