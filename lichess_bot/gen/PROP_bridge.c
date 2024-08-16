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
  return p_default_value;
}


/*
 * Bridge:  getInteger
 */
i_t
PROP_getInteger( const i_t p_default_value, c_t p_name[ESCHER_SYS_MAX_STRING_LEN], i_t p_properties )
{
  return p_default_value;
}


/*
 * Bridge:  getString
 */
c_t *
PROP_getString( c_t A0xtumlsret[ESCHER_SYS_MAX_STRING_LEN], c_t p_default_value[ESCHER_SYS_MAX_STRING_LEN], c_t p_name[ESCHER_SYS_MAX_STRING_LEN], i_t p_properties )
{
  Escher_strcpy(A0xtumlsret, p_default_value);
  return A0xtumlsret;
}


/*
 * Bridge:  loadProperties
 */
i_t
PROP_loadProperties( c_t p_file_name[ESCHER_SYS_MAX_STRING_LEN] )
{
  return 0;
}

