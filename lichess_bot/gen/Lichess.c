/*----------------------------------------------------------------------------
 * File:  Lichess.c
 *
 * UML Component Port Messages
 * Component/Module Name:  Lichess
 *
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#include "lichess_bot_sys_types.h"
#include "Lichess.h"
#include "ChessLib_bridge.h"
#include "PROP_bridge.h"
#include "ARCH_bridge.h"
#include "LOG_bridge.h"
#include "STRING_bridge.h"
#include "TIM_bridge.h"
#include "Engine.h"

/*
 * Interface:  LichessAPI
 * Provided Port:  API
 * To Provider Message:  abort
 */
bool
Lichess_API_abort( c_t p_game_id[ESCHER_SYS_MAX_STRING_LEN] )
{
  return true;
}

/*
 * Interface:  LichessAPI
 * Provided Port:  API
 * To Provider Message:  acceptChallenge
 */
bool
Lichess_API_acceptChallenge( c_t p_challenge_id[ESCHER_SYS_MAX_STRING_LEN] )
{
  char * front = "{\"name\":\"acceptChallenge\",\"args\":{\"challenge_id\":\"";
  char * back = "\"}}";
  FILE * f = fopen( "incoming/acceptChallenge.json", "w" );

  if ( f ) {
    debug_fprintf (stderr, "%s%s%s\n", front, p_challenge_id, back);
    fprintf (f, "%s%s%s\n", front, p_challenge_id, back);
    fclose (f);
  } else {
    debug_fprintf (stderr, "error opening incoming/acceptChallenge.json\n");
  }

  return true;
}

/*
 * Interface:  LichessAPI
 * Provided Port:  API
 * To Provider Message:  account
 */
lichess_bot_sdt_User _bot_user;
lichess_bot_sdt_User
Lichess_API_account()
{
  return _bot_user;
}

/*
 * Interface:  LichessAPI
 * Provided Port:  API
 * From Provider Message:  challenge
 */
void
Lichess_API_challenge( lichess_bot_sdt_Challenge p_challenge )
{
  Engine_chess_challenge(  p_challenge );
}

/*
 * Interface:  LichessAPI
 * Provided Port:  API
 * From Provider Message:  challengeCanceled
 */
void
Lichess_API_challengeCanceled( lichess_bot_sdt_Challenge p_challenge )
{
  Engine_chess_challengeCanceled(  p_challenge );
}

/*
 * Interface:  LichessAPI
 * Provided Port:  API
 * To Provider Message:  chat
 */
bool
Lichess_API_chat( c_t p_game_id[ESCHER_SYS_MAX_STRING_LEN], const lichess_bot_Room_t p_room, c_t p_text[ESCHER_SYS_MAX_STRING_LEN] )
{
  return true;
}

/*
 * Interface:  LichessAPI
 * Provided Port:  API
 * From Provider Message:  chatLine
 */
void
Lichess_API_chatLine( c_t p_game_id[ESCHER_SYS_MAX_STRING_LEN], const lichess_bot_Room_t p_room, c_t p_text[ESCHER_SYS_MAX_STRING_LEN], c_t p_username[ESCHER_SYS_MAX_STRING_LEN] )
{
  Engine_chess_chatLine(  p_game_id, p_room, p_text, p_username );
}

/*
 * Interface:  LichessAPI
 * Provided Port:  API
 * To Provider Message:  claimVictory
 */
bool
Lichess_API_claimVictory( c_t p_game_id[ESCHER_SYS_MAX_STRING_LEN] )
{
  return true;
}

/*
 * Interface:  LichessAPI
 * Provided Port:  API
 * To Provider Message:  connect
 */
void
Lichess_API_connect( i_t p_properties )
{
}

/*
 * Interface:  LichessAPI
 * Provided Port:  API
 * From Provider Message:  connected
 */
void
Lichess_API_connected()
{
  Engine_chess_connected();
}

/*
 * Interface:  LichessAPI
 * Provided Port:  API
 * To Provider Message:  createChallenge
 */
bool
Lichess_API_createChallenge( const i_t p_clock_increment, const i_t p_clock_limit, const lichess_bot_Color_t p_color, c_t p_fen[ESCHER_SYS_MAX_STRING_LEN], const bool p_rated, c_t p_user[ESCHER_SYS_MAX_STRING_LEN], const lichess_bot_Variant_t p_variant )
{
  /* Note that only the user, fen and clock values are used.  The rest is hard-coded.  */
  char * s = "{\"name\":\"createChallenge\",\"args\":{\
  \"user\":\"%s\",\
  \"clock_limit\":%d,\
  \"clock_increment\":%d,\
  \"rated\":false,\
  \"color\":\"RANDOM\",\
  \"fen\":\"%s\",\
  \"variant\":{\"key\":\"standard\"}}}\n";

  FILE * f = fopen( "incoming/createChallenge.json", "w" );
  if ( f ) {
    debug_fprintf (stderr, s, p_user, p_clock_limit, p_clock_increment, p_fen);
    fprintf (f, s, p_user, p_clock_limit, p_clock_increment, p_fen);
    fclose (f);
  } else {
    debug_fprintf (stderr, "error opening incoming/createChallenge.json\n");
  }
  return true;
}

/*
 * Interface:  LichessAPI
 * Provided Port:  API
 * To Provider Message:  declineChallenge
 */
bool
Lichess_API_declineChallenge( c_t p_challenge_id[ESCHER_SYS_MAX_STRING_LEN], const lichess_bot_DeclineReason_t p_reason )
{
  return true;
}

/*
 * Interface:  LichessAPI
 * Provided Port:  API
 * To Provider Message:  draw
 */
bool
Lichess_API_draw( const bool p_accept, c_t p_game_id[ESCHER_SYS_MAX_STRING_LEN] )
{
  return true;
}

/*
 * Interface:  LichessAPI
 * Provided Port:  API
 * From Provider Message:  error
 */
void
Lichess_API_error( lichess_bot_sdt_APIException p_error )
{
  Engine_chess_error(  p_error );
}

/*
 * Interface:  LichessAPI
 * Provided Port:  API
 * From Provider Message:  gameFinish
 */
void
Lichess_API_gameFinish( lichess_bot_sdt_GameEventInfo p_game_event )
{
  Engine_chess_gameFinish(  p_game_event );
}

/*
 * Interface:  LichessAPI
 * Provided Port:  API
 * From Provider Message:  gameFull
 */
void
Lichess_API_gameFull( lichess_bot_sdt_Game p_game )
{
  Engine_chess_gameFull(  p_game );
}

/*
 * Interface:  LichessAPI
 * Provided Port:  API
 * From Provider Message:  gameStart
 */
void
Lichess_API_gameStart( lichess_bot_sdt_GameEventInfo p_game_event )
{
  Engine_chess_gameStart(  p_game_event );
}

/*
 * Interface:  LichessAPI
 * Provided Port:  API
 * From Provider Message:  gameState
 */
void
Lichess_API_gameState( c_t p_game_id[ESCHER_SYS_MAX_STRING_LEN], lichess_bot_sdt_GameState p_game_state )
{
  Engine_chess_gameState(  p_game_id, p_game_state );
}

/*
 * Interface:  LichessAPI
 * Provided Port:  API
 * To Provider Message:  move
 */
bool
Lichess_API_move( c_t p_game_id[ESCHER_SYS_MAX_STRING_LEN], c_t p_move[ESCHER_SYS_MAX_STRING_LEN] )
{
  char * front = "{\"name\":\"move\",\"args\":{\"game_id\":\"";
  char * middle = "\",\"move\":\"";
  char * back = "\"}}";
  FILE * f = fopen( "incoming/move.json", "w" );

  if ( f ) {
    debug_fprintf (stderr, "%s%s%s%s%s\n", front, p_game_id, middle, p_move, back);
    fprintf (f, "%s%s%s%s%s\n", front, p_game_id, middle, p_move, back);
    fclose (f);
  } else {
    debug_fprintf (stderr, "error opening incoming/move.json\n");
  }
  return true;
}

/*
 * Interface:  LichessAPI
 * Provided Port:  API
 * From Provider Message:  opponentGone
 */
void
Lichess_API_opponentGone( const i_t p_claim_win_in_seconds, c_t p_game_id[ESCHER_SYS_MAX_STRING_LEN], const bool p_gone )
{
  Engine_chess_opponentGone(  p_claim_win_in_seconds, p_game_id, p_gone );
}

/*
 * Interface:  LichessAPI
 * Provided Port:  API
 * To Provider Message:  resign
 */
bool
Lichess_API_resign( c_t p_game_id[ESCHER_SYS_MAX_STRING_LEN] )
{
  return true;
}

/*
 * Interface:  LichessAPI
 * Provided Port:  API
 * To Provider Message:  takeback
 */
bool
Lichess_API_takeback( const bool p_accept, c_t p_game_id[ESCHER_SYS_MAX_STRING_LEN] )
{
  return true;
}
