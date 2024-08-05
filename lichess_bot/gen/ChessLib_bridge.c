/*----------------------------------------------------------------------------
 * Description:   Methods for bridging to an external entity.
 *
 * External Entity:  ChessLib (ChessLib)
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
#include "chesslib/chess.h"
#include <stdlib.h>

/*
 * Bridge:  destFile
 */
c_t *
ChessLib_destFile( c_t A0xtumlsret[ESCHER_SYS_MAX_STRING_LEN], c_t p_move[ESCHER_SYS_MAX_STRING_LEN] )
{
  c_t * result = 0;
  /* Insert your implementation code here... */
  return result;
}


/*
 * Bridge:  destRank
 */
i_t
ChessLib_destRank( c_t p_move[ESCHER_SYS_MAX_STRING_LEN] )
{
  i_t result = 0;
  /* Insert your implementation code here... */
  return result;
}


static const char * startpos = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
static char game_moves[1024][ESCHER_SYS_MAX_STRING_LEN];
static int game_move_count = 0;
/*
 * Bridge:  legalMoves
 */
i_t
ChessLib_legalMoves( c_t p_fen[ESCHER_SYS_MAX_STRING_LEN], c_t p_legal_moves[218][ESCHER_SYS_MAX_STRING_LEN] )
{
  i_t result = 0;
  printf("ChessLib_legalMoves\n");
  int i;
  move m;
  char * uci;
  chess *c = chessCreate();
  for ( i = 0; i < game_move_count; i++ ) {
    m = moveFromUci(game_moves[i]);
    uci = moveGetUci(m);
    printf("%s ", uci);
    free(uci);
    chessPlayMove(c, m);
  }
  moveList *moves = chessGetLegalMoves(c);
  printf("\nChessLib_populateLegalMoves:  Legal Moves list\n");
  for ( i = 0; i < moves->size && i < 218; i++ ) {
    m = moveListGet(moves, i);
    uci = moveGetUci(m);
    Escher_strcpy( p_legal_moves[i], uci );
    printf("%s ", uci);
    free(uci);
  }
  result = moves->size;
  chessFree(c);
  printf("END ChessLib_populateLegalMoves with count of legal moves:%d\n",result);
  return result;
}


/*
 * Bridge:  movesToFEN
 */
c_t *
ChessLib_movesToFEN( c_t A0xtumlsret[ESCHER_SYS_MAX_STRING_LEN], c_t p_initialFen[ESCHER_SYS_MAX_STRING_LEN], c_t p_moves[1024][ESCHER_SYS_MAX_STRING_LEN] )
{
  c_t * result = "";
  int i;
  fprintf(stderr,"START ChessLib_movesToFen\n");
  for ( i = 0; i < 1024; i++ ) {
    if ( Escher_strlen( p_moves[i] ) < 4 ) break;
    Escher_strcpy( game_moves[i], p_moves[i] );
  }
  game_move_count = i;
  fprintf(stderr,"END ChessLib_movesToFen with game_move_count:%d\n",game_move_count);
  return result;
}


/*
 * Bridge:  sourceFile
 */
c_t *
ChessLib_sourceFile( c_t A0xtumlsret[ESCHER_SYS_MAX_STRING_LEN], c_t p_move[ESCHER_SYS_MAX_STRING_LEN] )
{
  c_t * result = 0;
  /* Insert your implementation code here... */
  return result;
}


/*
 * Bridge:  sourceRank
 */
i_t
ChessLib_sourceRank( c_t p_move[ESCHER_SYS_MAX_STRING_LEN] )
{
  i_t result = 0;
  /* Insert your implementation code here... */
  return result;
}


/*
 * Bridge:  startpos
 */
c_t *
ChessLib_startpos( c_t A0xtumlsret[ESCHER_SYS_MAX_STRING_LEN] )
{
  c_t * result = 0;
  /* Insert your implementation code here... */
  return result;
}

