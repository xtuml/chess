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
  A0xtumlsret[0] = 0;
  if ( Escher_strlen( p_move ) >= 4 ) {
    A0xtumlsret[0] = p_move[3]; A0xtumlsret[1] = 0;
  }
  return A0xtumlsret;
}


/*
 * Bridge:  destRank
 */
i_t
ChessLib_destRank( c_t p_move[ESCHER_SYS_MAX_STRING_LEN] )
{
  char number[2];
  if ( Escher_strlen( p_move ) >= 4 ) {
    number[0] = p_move[2]; number[1] = 0;
    return STRING_atoi( number );
  } else {
    return 0;
  }
}


/*
 * Bridge:  legalMoves
 */
i_t
ChessLib_legalMoves( c_t p_fen[ESCHER_SYS_MAX_STRING_LEN], c_t p_legal_moves[218][ESCHER_SYS_MAX_STRING_LEN] )
{
  i_t result = 0;
  printf("ChessLib_legalMoves\n");
  int i;
  chess * c;
  move m;
  char * uci;
  if ( ( 0 == Escher_strcmp( p_fen, "" ) ) || ( 0 == p_fen[0] ) ) {
    c = chessCreate();
  } else {
    c = chessCreateFen(p_fen);
  }
  moveList *moves = chessGetLegalMoves(c);
  for ( i = 0; i < moves->size && i < 218; i++ ) {
    m = moveListGet(moves, i);
    uci = moveGetUci(m);
    Escher_strcpy( p_legal_moves[i], uci );
    printf("%s ", uci);
    free(uci);
  }
  result = moves->size;
  chessFree(c);
  printf("\nEND ChessLib_legalMoves with count of legal moves:%d\n",result);
  return result;
}


/*
 * Bridge:  movesToFEN
 */
c_t *
ChessLib_movesToFEN( c_t A0xtumlsret[ESCHER_SYS_MAX_STRING_LEN], c_t p_initialFen[ESCHER_SYS_MAX_STRING_LEN], c_t p_moves[1024][ESCHER_SYS_MAX_STRING_LEN] )
{
  int i, move_count = 0;
  chess * c;
  move m;
  char * uci;
  c = chessCreate();
  for ( i = 0; i < 1024; i++ ) {
    if ( Escher_strlen( p_moves[i] ) < 4 ) break;
  }
  move_count = i;
  for ( i = 0; i < move_count; i++ ) {
    m = moveFromUci(p_moves[i]);
    chessPlayMove(c, m);
  }
  Escher_strcpy( A0xtumlsret, chessGetFen(c) );
  chessFree(c);
  printf("ChessLib_movesToFen with move_count:%d\n",move_count);
  return A0xtumlsret;
}


/*
 * Bridge:  sourceFile
 */
c_t *
ChessLib_sourceFile( c_t A0xtumlsret[ESCHER_SYS_MAX_STRING_LEN], c_t p_move[ESCHER_SYS_MAX_STRING_LEN] )
{
  A0xtumlsret[0] = 0;
  if ( Escher_strlen( p_move ) >= 4 ) {
    A0xtumlsret[0] = p_move[0]; A0xtumlsret[1] = 0;
  }
  return A0xtumlsret;
}


/*
 * Bridge:  sourceRank
 */
i_t
ChessLib_sourceRank( c_t p_move[ESCHER_SYS_MAX_STRING_LEN] )
{
  char number[2];
  if ( Escher_strlen( p_move ) >= 4 ) {
    number[0] = p_move[0]; number[1] = 0;
    return STRING_atoi( number );
  } else {
    return 0;
  }
}


static const char * startpos = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
/*
 * Bridge:  startpos
 */
c_t *
ChessLib_startpos( c_t A0xtumlsret[ESCHER_SYS_MAX_STRING_LEN] )
{
  Escher_strcpy( A0xtumlsret, startpos );
  return A0xtumlsret;
}

