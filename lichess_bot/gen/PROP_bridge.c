/*----------------------------------------------------------------------------
 * Description:   Methods for bridging to an external entity.
 *
 * External Entity:  Properties (PROP)
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

/*
 * Bridge:  getBoolean
 */
bool
PROP_getBoolean( const bool p_default_value, c_t p_name[ESCHER_SYS_MAX_STRING_LEN], i_t p_properties )
{
  bool result = true;
  /* Insert your implementation code here... */
  return result;
}


/*
 * Bridge:  getInteger
 */
i_t
PROP_getInteger( const i_t p_default_value, c_t p_name[ESCHER_SYS_MAX_STRING_LEN], i_t p_properties )
{
  i_t result = 10;
  /* Insert your implementation code here... */
  return result;
}


/*
 * Bridge:  getString
 */
c_t *
PROP_getString( c_t A0xtumlsret[ESCHER_SYS_MAX_STRING_LEN], c_t p_default_value[ESCHER_SYS_MAX_STRING_LEN], c_t p_name[ESCHER_SYS_MAX_STRING_LEN], i_t p_properties )
{
  c_t * result = "https://lichess.org";
  /* Insert your implementation code here... */
  return result;
}


/*
 * Bridge:  loadProperties
 */
i_t
PROP_loadProperties( c_t p_file_name[ESCHER_SYS_MAX_STRING_LEN] )
{
  i_t result = 4;
  /* Insert your implementation code here... */
  return result;
}

