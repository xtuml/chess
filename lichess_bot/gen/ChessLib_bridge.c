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


/*
 * Bridge:  legalMoves
 */
c_t *
ChessLib_legalMoves( c_t A0xtumlsret[ESCHER_SYS_MAX_STRING_LEN], c_t p_fen[ESCHER_SYS_MAX_STRING_LEN] )
{
  c_t * result = 0;
  /* Insert your implementation code here... */
  return result;
}


/*
 * Bridge:  movesToFEN
 */
c_t *
ChessLib_movesToFEN( c_t A0xtumlsret[ESCHER_SYS_MAX_STRING_LEN], c_t p_initialFen[ESCHER_SYS_MAX_STRING_LEN], c_t p_moves[0][ESCHER_SYS_MAX_STRING_LEN] )
{
  c_t * result = 0;
  /* Insert your implementation code here... */
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


/*
 * Bridge:  populateLegalMoves
 */
i_t
ChessLib_populateLegalMoves( c_t p_legal_moves[32][ESCHER_SYS_MAX_STRING_LEN], const i_t p_move_count, c_t p_moves[32][ESCHER_SYS_MAX_STRING_LEN] )
{
  int result;
  /*
  Escher_strcpy( p_legal_moves[0], "a7a5" );
  Escher_strcpy( p_legal_moves[1], "b7b5" );
  Escher_strcpy( p_legal_moves[2], "c7c5" );
  Escher_strcpy( p_legal_moves[3], "d7d5" );
  Escher_strcpy( p_legal_moves[4], "e7e5" );
  Escher_strcpy( p_legal_moves[5], "f7f5" );
  Escher_strcpy( p_legal_moves[6], "g7g5" );
  Escher_strcpy( p_legal_moves[7], "h7h5" );
  */

  printf("ChessLib_populateLegalMoves\n");
  int i;
  move m;
  char * uci;
  chess *c = chessCreate();
  for ( i = 0; i < (p_move_count-1); i++ ) {
    m = moveFromUci(p_moves[i]);
    uci = moveGetUci(m);
    printf("%s ", uci);
    free(uci);
    chessPlayMove(c, m);
  }
  moveList *moves = chessGetLegalMoves(c);
  printf("\nChessLib_populateLegalMoves:  Legal Moves list\n");
  for ( i = 0; i < moves->size && i < 32; i++ ) {
    m = moveListGet(moves, i);
    uci = moveGetUci(m);
    Escher_strcpy( p_legal_moves[i], uci );
    printf("%s ", uci);
    free(uci);
  }
  chessFree(c);
  printf("END ChessLib_populateLegalMoves\n");

  return moves->size;
}

